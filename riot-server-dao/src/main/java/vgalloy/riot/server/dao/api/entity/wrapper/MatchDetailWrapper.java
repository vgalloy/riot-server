package vgalloy.riot.server.dao.api.entity.wrapper;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class MatchDetailWrapper extends AbstractItemWrapper<MatchDetail, MatchDetailId> {

    private static final long serialVersionUID = -3132015747453438540L;

    /**
     * Constructor.
     *
     * @param itemId the item id
     * @param item   the item
     */
    public MatchDetailWrapper(MatchDetailId itemId, MatchDetail item) {
        super(itemId, item);
    }

    /**
     * Constructor.
     *
     * @param itemId the item id
     */
    public MatchDetailWrapper(MatchDetailId itemId) {
        super(itemId);
    }

    @Override
    public String toString() {
        return "MatchDetailWrapper{" +
                "itemId=" + getItemId() +
                ", item=" + getItem() +
                '}';
    }
}
