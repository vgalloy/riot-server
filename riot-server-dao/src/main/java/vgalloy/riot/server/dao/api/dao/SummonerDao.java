package vgalloy.riot.server.dao.api.dao;

import java.util.List;

import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.GetSummonersQuery;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * Created by Vincent Galloy on 28/08/16.
 *
 * @author Vincent Galloy
 */
public interface SummonerDao extends CommonDao<SummonerDto> {

    /**
     * Get the summoner by name and region.
     *
     * @param getSummonersQuery the param for the query
     * @return the summoner'id matching with request
     */
    List<Entity<SummonerDto, DpoId>> getSummoners(GetSummonersQuery getSummonersQuery);
}
