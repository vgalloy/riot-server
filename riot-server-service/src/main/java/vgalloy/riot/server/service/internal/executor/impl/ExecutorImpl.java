package vgalloy.riot.server.service.internal.executor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.model.RiotApiKey;
import vgalloy.riot.api.api.query.AbstractQuery;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.RegionExecutor;
import vgalloy.riot.server.service.internal.executor.model.Request;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/07/16.
 */
public class ExecutorImpl implements Executor {

    private final Map<Region, RegionExecutor> regionExecutorMap = new HashMap<>();
    private final RiotApiKey riotApiKey;

    /**
     * Constructor.
     *
     * @param riotApiKey the riot api key
     */
    public ExecutorImpl(RiotApiKey riotApiKey) {
        this.riotApiKey = Objects.requireNonNull(riotApiKey, "riotApiKey can not be null");
        Stream.of(Region.values()).forEach(e -> regionExecutorMap.put(e, new RegionExecutorImpl(e)));
    }

    @Override
    public <DTO> DTO execute(Query<DTO> query, Region region, int priority) {
        Objects.requireNonNull(region);
        if (query instanceof AbstractQuery) {
            ((AbstractQuery<?, DTO>) query).riotApiKey(riotApiKey).region(region);
        }
        Request<DTO> request = new Request<>(query, priority);
        return regionExecutorMap.get(region).execute(request);
    }
}
