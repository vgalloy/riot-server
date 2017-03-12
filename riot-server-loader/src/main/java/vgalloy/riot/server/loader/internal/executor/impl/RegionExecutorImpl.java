package vgalloy.riot.server.loader.internal.executor.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

import javax.ws.rs.client.ResponseProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.api.internal.client.filter.RiotRateLimitExceededException;
import vgalloy.riot.server.loader.api.service.exception.LoaderException;
import vgalloy.riot.server.loader.internal.executor.RegionExecutor;
import vgalloy.riot.server.loader.internal.executor.helper.CycleManager;
import vgalloy.riot.server.loader.internal.executor.model.Request;
import vgalloy.riot.server.loader.internal.helper.RegionPrinter;

/**
 * Created by Vincent Galloy on 30/06/16.
 *
 * @author Vincent Galloy
 */
public final class RegionExecutorImpl implements RegionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionExecutorImpl.class);

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
        try {
            return execute(request.getQuery());
        } finally {
            synchronized (this) {
                electedRequest = null;
                notifyAll();
            }
        }
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
        long max = requestList.stream()
                .map(Request::getPriority)
                .mapToInt(Integer::intValue)
                .sum();
        long rand = Math.abs(random.nextLong() % max);
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
        CycleManager cycleManager = new CycleManager();
        do {
            try {
                return query.execute();
            } catch (RiotRateLimitExceededException e) {
                if (e.getRetryAfter().isPresent()) {
                    throw new LoaderException(e);
                }
                LOGGER.warn("{} : {}, sleepingTimeMillis = {} ms, attempt : {}", RegionPrinter.getRegion(region), e.toString(), cycleManager.getSleepingTimeMillis(), cycleManager.getIteration());
            } catch (ResponseProcessingException e) {
                throw new LoaderException("Unable to deserialize the query", e);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            cycleManager.sleep();
        } while (cycleManager.tryAgain());
        throw new LoaderException("After " + cycleManager.getIteration() + " attempts and " +
                cycleManager.totalExecutionTimeMillis() + " ms, I give up. I can not load the query : " + query.toString());
    }
}
