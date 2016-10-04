package vgalloy.riot.server.service.internal.loader.helper;

import org.slf4j.Logger;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.service.exception.ServiceException;

/**
 * @author Vincent Galloy - 05/10/16
 *         Created by Vincent Galloy on 05/10/16.
 */
public final class LoaderHelper {

    /**
     * Constructor.
     * <p>
     * To prevent instantiation
     */
    private LoaderHelper() {
        throw new AssertionError();
    }

    /**
     * Return a random summoner id.
     *
     * @param summonerDao the summoner dao
     * @param region      the region
     * @param logger      the logger
     * @return a random summoner id
     */
    public static long getRandomSummonerId(SummonerDao summonerDao, Region region, Logger logger) {
        long sleepingTime = 0;
        while (true) {
            Optional<Entity<SummonerDto>> summonerEntity = summonerDao.getRandom(region);
            if (summonerEntity.isPresent() && summonerEntity.get().getItem().isPresent()) {
                return summonerEntity.get().getItem().get().getId();
            } else {
                logger.warn("{} : No summonerId found", RegionPrinter.getRegion(region));
                try {
                    sleepingTime += 1_000;
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    /**
     * Return a random match id.
     *
     * @param matchReferenceDao the match reference dao
     * @param region            the region
     * @param logger            the logger
     * @return a random match id
     */
    public static long getRandomMatchId(MatchReferenceDao matchReferenceDao, Region region, Logger logger) {
        long sleepingTime = 0;
        while (true) {
            Optional<Entity<MatchReference>> matchReferenceEntity = matchReferenceDao.getRandom(region);
            if (matchReferenceEntity.isPresent() && matchReferenceEntity.get().getItem().isPresent()) {
                return matchReferenceEntity.get().getItem().get().getMatchId();
            } else {
                logger.warn("{} : No matchReference found", RegionPrinter.getRegion(region));
                try {
                    sleepingTime += 1_000;
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }
}
