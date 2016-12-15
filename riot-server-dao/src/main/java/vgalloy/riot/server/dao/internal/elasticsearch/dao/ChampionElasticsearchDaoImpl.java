package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class ChampionElasticsearchDaoImpl implements ChampionDao {

    @Override
    public void save(CommonDpoWrapper<ChampionDto> itemWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<ChampionDto, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<ChampionDto, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<LocalDate, WinRate> getWinRate(int championId, LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WinRate getWinRate(int championId, LocalDate localDate) {
        throw new UnsupportedOperationException();
    }
}
