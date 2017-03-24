package vgalloy.riot.server.dao.internal.entity.dpo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerDpo extends AbstractDpo<SummonerDto> {

    /**
     * Constructor.
     *
     * @param lastUpdate the last update
     * @param region     the region of the item
     * @param itemId     the item id
     * @param item       the item
     */
    @JsonCreator
    public SummonerDpo(@JsonProperty("lastUpdate") Long lastUpdate,
                       @JsonProperty("region") Region region,
                       @JsonProperty("itemId") Long itemId,
                       @JsonProperty("item") SummonerDto item) {
        super(lastUpdate, region, itemId, item);
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public SummonerDpo(Region region, Long itemId) {
        super(region, itemId);
    }
}
