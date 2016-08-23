package vgalloy.riot.server.service.internal.loader.impl.matchreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchList;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.api.service.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 *         Need : Summoner
 *         Load : MatchReference
 */
public class MatchReferenceLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchReferenceLoader.class);

    private final Region region;

    @Autowired
    private CommonDao<SummonerDto> summonerService;
    @Autowired
    private CommonDao<MatchReference> matchReferenceService;

    /**
     * Constructor.
     *
     * @param region the region
     */
    public MatchReferenceLoader(Region region) {
        this.region = Objects.requireNonNull(region, "region can not be null");
    }

    @Override
    public void execute() {
        while (true) {
            Optional<Entity<SummonerDto>> summonerEntity = summonerService.getRandom(region);
            if (summonerEntity.isPresent()) {
                long summonerId = summonerEntity.get().getItem().getId();
                List<MatchReference> matchReferences = load(summonerId);
                matchReferences.forEach(e -> matchReferenceService.save(region, e.getMatchId(), e));
            } else {
                LOGGER.warn("{} : No summoner found", RegionPrinter.getRegion(region));
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    /**
     * Load the ranked stats for a given summoner.
     *
     * @param summonerId the summoner id
     * @return the RankedStatsDto
     */
    private List<MatchReference> load(long summonerId) {
        loaderInformation.addRequest();
        loaderInformation.addRankedStatsRequest();
        Query<MatchList> query = riotApi.getMatchListBySummonerId(summonerId).region(region);
        MatchList matchList = executor.execute(query, region, 1);
        if (matchList == null || matchList.getMatches() == null) {
            return new ArrayList<>();
        }
        LOGGER.info("{} : {} - {} ", RegionPrinter.getRegion(region), summonerId, loaderInformation.printInformation());
        return matchList.getMatches();
    }
}
