package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class RankedStatsDo extends DataObject<RankedStatsDto> {

    /**
     * Constructor. For Jackson deserialization.
     */
    private RankedStatsDo() {

    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     * @param item   the item
     */
    public RankedStatsDo(Region region, Long itemId, RankedStatsDto item) {
        super(region, itemId, item);
    }
}
