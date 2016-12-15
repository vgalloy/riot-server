package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class SummonerElasticsearchDaoImpl implements SummonerDao {

    @Override
    public void save(CommonDpoWrapper<SummonerDto> dpoWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<SummonerDto, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<SummonerDto, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<SummonerDto> getSummonerByName(Region region, String summonerName) {
        throw new UnsupportedOperationException();
    }
}
