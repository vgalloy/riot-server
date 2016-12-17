package vgalloy.riot.server.dao.internal.entity.dpo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class ChampionDpo extends AbstractDpo<ChampionDto> {

    /**
     * Constructor.
     *
     * @param lastUpdate the last update
     * @param region     the region of the item
     * @param itemId     the item id
     * @param item       the item
     * @param id         the id
     */
    @JsonCreator
    public ChampionDpo(@JsonProperty("lastUpdate") Long lastUpdate,
                       @JsonProperty("region") Region region,
                       @JsonProperty("itemId") Long itemId,
                       @JsonProperty("item") ChampionDto item,
                       @JsonProperty("_id") String id) {
        super(lastUpdate, region, itemId, item, id);
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public ChampionDpo(Region region, Long itemId) {
        super(region, itemId);
    }
}
