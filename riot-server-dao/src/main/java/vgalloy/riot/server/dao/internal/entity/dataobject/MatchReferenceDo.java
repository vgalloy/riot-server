package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class MatchReferenceDo extends DataObject<MatchReference> {
    /**
     * Constructor. For Jackson deserialization.
     */
    private MatchReferenceDo() {

    }
    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     * @param item   the item
     */
    public MatchReferenceDo(Region region, Long itemId, MatchReference item) {
        super(region, itemId, item);
    }
}
