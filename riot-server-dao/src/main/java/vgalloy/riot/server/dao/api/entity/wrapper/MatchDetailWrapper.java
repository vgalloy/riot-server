package vgalloy.riot.server.dao.api.entity.wrapper;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class MatchDetailWrapper extends AbstractDpoWrapper<MatchDetail, MatchDetailId> {

    private static final long serialVersionUID = -3132015747453438540L;

    /**
     * Constructor.
     *
     * @param itemId the item id
     * @param item   the item (can be null)
     */
    public MatchDetailWrapper(MatchDetailId itemId, MatchDetail item) {
        super(itemId, item);
    }

    @Override
    public String toString() {
        return "MatchDetailWrapper{" +
                "itemId=" + getItemId() +
                ", item=" + getItem() +
                '}';
    }
}
