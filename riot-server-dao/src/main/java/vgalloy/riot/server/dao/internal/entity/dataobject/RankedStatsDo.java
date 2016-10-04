package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;

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
     */
    public RankedStatsDo(Region region, Long itemId) {
        super(region, itemId);
    }
}
