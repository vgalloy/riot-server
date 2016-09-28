package vgalloy.riot.server.service.internal.executor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.api.internal.client.filter.RiotRateLimitExceededException;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.RegionExecutor;
import vgalloy.riot.server.service.internal.executor.model.Request;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

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

    private long sleepingTimeMillis = DEFAULT_SLEEPING_TIME_MILLIS;
    private Request<?> electedRequest = null;

    /**
     * Constructor.
     *
     * @param region the region
     */
    public RegionExecutorImpl(Region region) {
        this.region = Objects.requireNonNull(region, "region can not be null");
    }

    @Override
    public <DTO> DTO execute(Request<DTO> request) {
        synchronized (this) {
            requestList.add(request);
            while (amITheChosenOne(request)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
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
        long max = requestList.stream().map(Request::getPriority).count();
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
        for (int attempt = 1; attempt < 10; attempt++) {
            try {
                DTO result = query.execute();
                sleepingTimeMillis = Math.max(sleepingTimeMillis / 2, DEFAULT_SLEEPING_TIME_MILLIS);
                return result;
            } catch (RiotRateLimitExceededException e) {
                LOGGER.warn("{} : {}, sleepingTimeMillis = {} ms, attempt : {}", RegionPrinter.getRegion(region), e.toString(), sleepingTimeMillis, attempt);
                if (e.getRetryAfter().isPresent()) {
                    throw new ServiceException(e);
                }
                try {
                    Thread.sleep(sleepingTimeMillis);
                } catch (InterruptedException e1) {
                    throw new ServiceException(e1);
                }
                sleepingTimeMillis *= 4;
            }
        }
        return null;
    }
}
