package vgalloy.riot.server.service.internal.loader.impl.intializer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/07/16.
 */
public class LoaderInitializer extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderInitializer.class);

    private final CommonDao<SummonerDto> summonerDao;

    /**
     * Constructor.
     *
     * @param riotApi     the riot api
     * @param executor    the executor
     * @param summonerDao the summoner dao
     */
    public LoaderInitializer(RiotApi riotApi, Executor executor, CommonDao<SummonerDto> summonerDao) {
        super(riotApi, executor);
        this.summonerDao = Objects.requireNonNull(summonerDao);
    }

    @Override
    public void execute() {
        load(Region.EUNE, 18986053);
        load(Region.EUW, 24550736);
        load(Region.NA, 22577485);
        load(Region.KR, 8130147);
        load(Region.BR, 810720);
    }

    /**
     * Load the summoner on the given region.
     *
     * @param region     the region
     * @param summonerId the summonerId
     */
    private void load(Region region, int summonerId) {
        Map<String, SummonerDto> result = executor.execute(riotApi.getSummonersByIds(summonerId), region, 1);
        if (result == null || result.size() != 1) {
            throw new ServiceException(RegionPrinter.getRegion(region) + " Error while loading summoner - " + summonerId);
        }
        for (Entry<String, SummonerDto> entry : result.entrySet()) {
            summonerDao.save(new CommonWrapper<>(new ItemId(region, (long) summonerId), entry.getValue()));
            LOGGER.info("{} : Load summoner {}", RegionPrinter.getRegion(region), summonerId);
        }
    }
}
