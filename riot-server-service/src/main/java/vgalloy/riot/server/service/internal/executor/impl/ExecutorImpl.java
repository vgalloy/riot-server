package vgalloy.riot.server.service.internal.executor.impl;

import org.springframework.stereotype.Component;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.service.RiotApiKey;
import vgalloy.riot.api.service.query.AbstractQuery;
import vgalloy.riot.api.service.query.Query;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.RegionExecutor;
import vgalloy.riot.server.service.internal.executor.model.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/07/16.
 */
@Component
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
        for (Region region : Region.values()) {
            regionExecutorMap.put(region, new RegionExecutorImpl(region));
        }
    }

    @Override
    public <DTO> DTO execute(Query<DTO> query, Region region, int priority) {
        if (query instanceof AbstractQuery) {
            ((AbstractQuery<?, DTO>) query).riotApiKey(riotApiKey).region(region);
        }
        Request<DTO> request = new Request<>(query, priority);
        return regionExecutorMap.get(region).execute(request);
    }
}
