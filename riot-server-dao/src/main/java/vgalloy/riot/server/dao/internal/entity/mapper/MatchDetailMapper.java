package vgalloy.riot.server.dao.internal.entity.mapper;

import java.time.LocalDate;
import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.internal.entity.dataobject.MatchDetailDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
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
     * @param matchDetailDo the match detail database object
     * @return the entity
     */
    public static Entity<MatchDetail, MatchDetailId> mapToEntity(MatchDetailDo matchDetailDo) {
        Objects.requireNonNull(matchDetailDo);

        ItemId itemId = ItemIdMapper.fromNormalize(matchDetailDo.getId());
        MatchDetailId matchDetailId = new MatchDetailId(itemId.getRegion(), itemId.getId(), LocalDate.ofEpochDay(matchDetailDo.getMatchCreationDateFromEpochDay()));
        return new Entity<>(matchDetailId, matchDetailDo.getItem(), matchDetailDo.getLastUpdate());
    }
}
