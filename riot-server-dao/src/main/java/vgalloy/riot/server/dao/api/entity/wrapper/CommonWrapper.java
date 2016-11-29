package vgalloy.riot.server.dao.api.entity.wrapper;

import vgalloy.riot.server.dao.api.entity.itemid.ItemId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class CommonWrapper<DTO> extends AbstractItemWrapper<DTO, ItemId> {

    private static final long serialVersionUID = -4168382974522328793L;

    /**
     * Constructor.
     *
     * @param itemId the item id
     * @param item   the item
     */
    public CommonWrapper(ItemId itemId, DTO item) {
        super(itemId, item);
    }

    /**
     * Constructor.
     *
     * @param itemId the item id
     */
    public CommonWrapper(ItemId itemId) {
        super(itemId);
    }

    @Override
    public String toString() {
        return "AbstractItemWrapper{" +
                "itemId=" + getItemId() +
                ", item=" + getItem() +
                '}';
    }
}
