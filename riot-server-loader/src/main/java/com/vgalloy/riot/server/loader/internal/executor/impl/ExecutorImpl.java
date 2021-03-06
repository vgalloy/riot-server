package com.vgalloy.riot.server.loader.internal.executor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.model.RiotApiKey;
import vgalloy.riot.api.api.query.AbstractQuery;
import vgalloy.riot.api.api.query.Query;

import com.vgalloy.riot.server.loader.internal.executor.Executor;
import com.vgalloy.riot.server.loader.internal.executor.RegionExecutor;
import com.vgalloy.riot.server.loader.internal.executor.model.Request;

/**
 * Created by Vincent Galloy on 04/07/16.
 *
 * @author Vincent Galloy
 */
public final class ExecutorImpl implements Executor {

    private final Map<Region, RegionExecutor> regionExecutorMap = new HashMap<>();
    private final RiotApiKey riotApiKey;

    /**
     * Constructor.
     *
     * @param riotApiKey the riot api key
     */
    public ExecutorImpl(RiotApiKey riotApiKey) {
        this.riotApiKey = Objects.requireNonNull(riotApiKey);
        Stream.of(Region.values()).forEach(e -> regionExecutorMap.put(e, new RegionExecutorImpl(e)));
    }

    @Override
    public <DTO> DTO execute(Query<DTO> query, Region region, int priority) {
        Objects.requireNonNull(query);
        Objects.requireNonNull(region);
        if (query instanceof AbstractQuery) {
            ((AbstractQuery<?, DTO>) query).riotApiKey(riotApiKey).region(region);
        }
        Request<DTO> request = new Request<>(query, priority);
        return regionExecutorMap.get(region).execute(request);
    }
}
