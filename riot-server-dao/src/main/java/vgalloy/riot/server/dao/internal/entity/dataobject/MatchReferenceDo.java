package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;

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
     */
    public MatchReferenceDo(Region region, Long itemId) {
        super(region, itemId);
    }
}
