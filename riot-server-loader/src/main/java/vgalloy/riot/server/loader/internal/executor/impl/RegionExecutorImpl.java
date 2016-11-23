package vgalloy.riot.server.loader.internal.executor.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.api.internal.client.filter.RiotRateLimitExceededException;
import vgalloy.riot.server.loader.api.service.exception.LoaderException;
import vgalloy.riot.server.loader.internal.executor.RegionExecutor;
import vgalloy.riot.server.loader.internal.executor.model.Request;
import vgalloy.riot.server.loader.internal.loader.helper.RegionPrinter;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public final class RegionExecutorImpl implements RegionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionExecutorImpl.class);
    private static final long DEFAULT_SLEEPING_TIME_MILLIS = 2_000;

    private final Random random = new SecureRandom();
    private final Collection<Request<?>> requestList = new ArrayList<>();
    private final Region region;

    private Request<?> electedRequest = null;

    /**
     * Constructor.
     *
     * @param region the region
     */
    public RegionExecutorImpl(Region region) {
        this.region = Objects.requireNonNull(region);
    }

    @Override
    public <DTO> DTO execute(Request<DTO> request) {
        synchronized (this) {
            requestList.add(request);
            while (amITheChosenOne(request)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new LoaderException(e);
                }
            }
        }
        DTO result = execute(request.getQuery());
        synchronized (this) {
            electedRequest = null;
            notifyAll();
        }
        return result;
    }

    /**
     * Is the request the elected request.
     *
     * @param request the request of the current thread
     * @return true if the request must be executed now
     */
    private boolean amITheChosenOne(Request<?> request) {
        if (electedRequest == null) {
            election();
        }
        return request != electedRequest;
    }

    /**
     * Chose which request have to be executed.
     */
    private void election() {
        long max = requestList.stream().map(Request::getPriority).mapToInt(Integer::intValue).sum();
        long rand = Math.abs(random.nextLong()) % max;
        for (Request<?> request : requestList) {
            if (rand < request.getPriority()) {
                electedRequest = request;
                break;
            }
            rand -= request.getPriority();
        }
        requestList.remove(electedRequest);
    }

    /**
     * Execute the query.
     *
     * @param query the query
     * @param <DTO> the dto type
     * @return the dto
     */
    private <DTO> DTO execute(Query<DTO> query) {
        long sleepingTimeMillis = DEFAULT_SLEEPING_TIME_MILLIS;
        int maxAttempt = 20;
        for (int attempt = 1; attempt < maxAttempt; attempt++) {
            try {
                DTO result = query.execute();
                sleepingTimeMillis = Math.max(sleepingTimeMillis / 2, DEFAULT_SLEEPING_TIME_MILLIS);
                return result;
            } catch (RiotRateLimitExceededException e) {
                LOGGER.warn("{} : {}, sleepingTimeMillis = {} ms, attempt : {}", RegionPrinter.getRegion(region), e.toString(), sleepingTimeMillis, attempt);
                if (e.getRetryAfter().isPresent()) {
                    throw new LoaderException(e);
                }
                sleep(sleepingTimeMillis);
                sleepingTimeMillis *= 2;
            } catch (Exception e) {
                LOGGER.error("{}", e);
                sleep(sleepingTimeMillis);
                sleepingTimeMillis *= 2;
            }
        }
        throw new LoaderException("After " + maxAttempt + " attempts, I give up. I can load the query : " + query.toString());
    }

    /**
     * Slow down the request execution.
     *
     * @param sleepingTimeMillis the sleeping time (in millis)
     */
    private static void sleep(long sleepingTimeMillis) {
        try {
            Thread.sleep(sleepingTimeMillis);
        } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
        }
    }
}
