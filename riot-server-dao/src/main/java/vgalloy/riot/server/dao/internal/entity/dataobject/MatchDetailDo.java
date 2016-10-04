package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class MatchDetailDo extends DataObject<MatchDetail> {

    /**
     * Constructor. For Jackson deserialization.
     */
    private MatchDetailDo() {

    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public MatchDetailDo(Region region, Long itemId) {
        super(region, itemId);
    }
}
