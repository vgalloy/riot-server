package com.vgalloy.riot.server.loader.internal.loader.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.api.api.model.RiotApi;

import com.vgalloy.riot.server.dao.api.dao.ChampionDao;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import com.vgalloy.riot.server.loader.internal.executor.Executor;
import com.vgalloy.riot.server.loader.internal.helper.RegionPrinter;
import com.vgalloy.riot.server.loader.internal.loader.ChampionLoader;

/**
 * Created by Vincent Galloy on 07/12/16.
 *
 * @author Vincent Galloy
 */
public final class ChampionLoaderImpl implements ChampionLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionLoaderImpl.class);

    private final RiotApi riotApi;
    private final Executor executor;
    private final ChampionDao championDao;

    /**
     * Constructor.
     *
     * @param riotApi     the riot api
     * @param executor    the executor
     * @param championDao the champion dao
     */
    public ChampionLoaderImpl(RiotApi riotApi, Executor executor, ChampionDao championDao) {
        this.riotApi = Objects.requireNonNull(riotApi);
        this.executor = Objects.requireNonNull(executor);
        this.championDao = Objects.requireNonNull(championDao);
    }

    @Override
    public void loadChampionById(Region region, Long championId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(championId);
        LOGGER.info("{} load full champion with id : {}", RegionPrinter.getRegion(region), championId);
        DpoId item = new CommonDpoId(region, championId);

        ChampionDto championDto = executor.execute(riotApi.getChampionDataById(item.getId()), item.getRegion(), 1);
        championDao.save(new CommonDpoWrapper<>(item, championDto));
    }
}
