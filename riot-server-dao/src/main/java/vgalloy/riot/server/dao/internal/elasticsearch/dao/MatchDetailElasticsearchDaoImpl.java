package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class MatchDetailElasticsearchDaoImpl implements MatchDetailDao {

    @Override
    public void save(MatchDetailWrapper matchDetailWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MatchDetail> findMatchDetailBySummonerId(Region region, long summonerId, LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException();
    }
}
