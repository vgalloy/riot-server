package vgalloy.riot.server.dao.api.entity.wrapper;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.server.dao.api.entity.itemid.ItemId;

/**
 * @author Vincent Galloy - 05/10/16
 *         Created by Vincent Galloy on 05/10/16.
 */
public abstract class AbstractItemWrapper<DTO, ID extends ItemId> implements Serializable {

    private static final long serialVersionUID = 5745585472522922118L;

    private final ID itemId;
    private final DTO item;

    /**
     * @param itemId the item id
     * @param item   the item
     */
    public AbstractItemWrapper(ID itemId, DTO item) {
        this.itemId = Objects.requireNonNull(itemId);
        this.item = Objects.requireNonNull(item);
    }

    /**
     * @param itemId the item id
     */
    public AbstractItemWrapper(ID itemId) {
        this.itemId = Objects.requireNonNull(itemId);
        item = null;
    }

    public ID getItemId() {
        return itemId;
    }

    public Optional<DTO> getItem() {
        return Optional.ofNullable(item);
    }
}
