package vgalloy.riot.server.dao.api.dao;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;

import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public interface MatchDetailDao extends CommonDao<MatchDetail> {

    /**
     * Get the last games of a summoner.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param limit      the limit of result to fetch
     * @return the last games
     */
    List<MatchDetail> getLastMatchDetail(Region region, long summonerId, int limit);
}
