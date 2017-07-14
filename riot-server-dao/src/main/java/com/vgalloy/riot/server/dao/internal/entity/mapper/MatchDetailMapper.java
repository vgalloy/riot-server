package com.vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.MatchDetail;

import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import com.vgalloy.riot.server.dao.api.mapper.MatchDetailIdMapper;
import com.vgalloy.riot.server.dao.internal.entity.dpo.MatchDetailDpo;

/**
 * Created by Vincent Galloy on 12/07/16.
 *
 * @author Vincent Galloy
 */
public final class MatchDetailMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MatchDetailMapper() {
        throw new AssertionError();
    }

    /**
     * Converts a match detail database object into an match detail wrapper.
     *
     * @param matchDetailDpo the match detail database object
     * @return the entity
     */
    public static Entity<MatchDetail, MatchDetailId> mapToEntity(MatchDetailDpo matchDetailDpo) {
        Objects.requireNonNull(matchDetailDpo);

        MatchDetailId matchDetailId = MatchDetailIdMapper.map(matchDetailDpo);
        return new Entity<>(matchDetailId, matchDetailDpo.getItem(), matchDetailDpo.getLastUpdate());
    }
}
