package vgalloy.riot.server.dao.internal.entity.mapper;

import java.time.LocalDate;
import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
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
    public static Entity<MatchDetailWrapper> mapToEntity(MatchDetailDo matchDetailDo) {
        Objects.requireNonNull(matchDetailDo);

        MatchDetailWrapper wrapper = mapToWrapper(matchDetailDo);
        return new Entity<>(wrapper, matchDetailDo.getLastUpdate());
    }

    /**
     * Converts a match detail database object into an match detail wrapper.
     *
     * @param matchDetailDo the match detail database object
     * @return the common wrapper
     */
    private static MatchDetailWrapper mapToWrapper(MatchDetailDo matchDetailDo) {
        Objects.requireNonNull(matchDetailDo);

        ItemId itemId = ItemIdMapper.fromNormalize(matchDetailDo.getId());
        if (matchDetailDo.getItem() == null) {
            return new MatchDetailWrapper(new MatchDetailId(itemId.getRegion(), itemId.getId(), LocalDate.ofEpochDay(matchDetailDo.getMatchCreationDateFromEpochDay())));
        }
        return new MatchDetailWrapper(new MatchDetailId(itemId.getRegion(), itemId.getId(), LocalDate.ofEpochDay(matchDetailDo.getMatchCreationDateFromEpochDay())), matchDetailDo.getItem());
    }
}
