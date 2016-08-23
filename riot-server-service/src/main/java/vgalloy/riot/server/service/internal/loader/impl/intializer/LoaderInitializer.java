package vgalloy.riot.server.service.internal.loader.impl.intializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.service.api.model.LoaderInformation;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/07/16.
 */
public class LoaderInitializer extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderInitializer.class);

    private final LoaderInformation loaderInformation = new LoaderInformation();

    @Autowired
    private RiotApi riotApi;
    @Autowired
    private Executor executor;
    @Autowired
    private CommonDao<SummonerDto> summonerDao;

    @Override
    public LoaderInformation getLoaderInformation() {
        return loaderInformation;
    }

    @Override
    public void run() {
        load(Region.eune, 18986053);
        load(Region.euw, 24550736);
        load(Region.na, 22577485);
        load(Region.kr, 8130147);
        load(Region.br, 810720);
        loaderInformation.setRunning(false);
    }

    /**
     * Load the summoner on the given region.
     *
     * @param region     the region
     * @param summonerId the summonerId
     */
    private void load(Region region, int summonerId) {
        Map<String, SummonerDto> result = executor.execute(riotApi.getSummonersByIds(summonerId), region, 1);
        if (result.size() != 1) {
            throw new ServiceException(region + " Error while loading summoner");
        }
        for (Entry<String, SummonerDto> entry : result.entrySet()) {
            summonerDao.save(region, (long) summonerId, entry.getValue());
            LOGGER.info("{} : Load summoner {}", RegionPrinter.getRegion(region), summonerId);
        }
    }
}
