package com.vgalloy.riot.server.dao.internal.entity.dpo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.Timeline;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class TimelineDpo extends AbstractDpo<Timeline> {

    /**
     * Constructor.
     *
     * @param lastUpdate the last update
     * @param region     the region of the item
     * @param itemId     the item id
     * @param item       the item
     */
    @JsonCreator
    public TimelineDpo(@JsonProperty("lastUpdate") Long lastUpdate,
                       @JsonProperty("region") Region region,
                       @JsonProperty("itemId") Long itemId,
                       @JsonProperty("item") Timeline item) {
        super(lastUpdate, region, itemId, item);
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public TimelineDpo(Region region, Long itemId) {
        super(region, itemId);
    }
}
