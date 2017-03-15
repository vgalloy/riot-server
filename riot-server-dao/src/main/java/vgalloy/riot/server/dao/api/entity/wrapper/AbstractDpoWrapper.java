package vgalloy.riot.server.dao.api.entity.wrapper;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * Created by Vincent Galloy on 05/10/16.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractDpoWrapper<DTO, ID extends DpoId> implements Serializable {

    private static final long serialVersionUID = 5745585472522922118L;

    private final ID itemId;
    private final DTO item;

    /**
     * @param itemId the item id
     * @param item   the item (can be null)
     */
    protected AbstractDpoWrapper(ID itemId, DTO item) {
        this.itemId = Objects.requireNonNull(itemId);
        this.item = item;
    }

    public ID getItemId() {
        return itemId;
    }

    public Optional<DTO> getItem() {
        return Optional.ofNullable(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractDpoWrapper<?, ?> that = (AbstractDpoWrapper<?, ?>) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, item);
    }
}
