package vgalloy.riot.server.service.internal.executor.impl;

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
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.RegionExecutor;
import vgalloy.riot.server.service.internal.executor.model.Request;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class RegionExecutorImpl implements RegionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionExecutorImpl.class);
    private static final long DEFAULT_SLEEPING_TIME = 2_000;

    private final Random random = new SecureRandom();
    private final Collection<Request<?>> requestList = new ArrayList<>();
    private final Region region;

    private long sleepingTime = DEFAULT_SLEEPING_TIME;
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
    // TODO retry ?
    private <DTO> DTO execute(Query<DTO> query) {
        DTO result = null;
        try {
            result = query.execute();
            sleepingTime = Math.max(sleepingTime / 2, DEFAULT_SLEEPING_TIME);
        } catch (RiotRateLimitExceededException e) {
            LOGGER.warn("{} : {}, sleepingTime = {}", RegionPrinter.getRegion(region), e.toString(), sleepingTime);
            if (e.getRetryAfter().isPresent()) {
                throw new ServiceException(e);
            }
            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e1) {
                throw new ServiceException(e1);
            }
            sleepingTime *= 4;
        }
        return result;
    }
}
