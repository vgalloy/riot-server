package vgalloy.riot.server.dao.api.entity.wrapper;

import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class CommonDpoWrapper<DTO> extends AbstractDpoWrapper<DTO, DpoId> {

    private static final long serialVersionUID = -4168382974522328793L;

    /**
     * Constructor.
     *
     * @param dpoId the item id
     * @param item  the item
     */
    public CommonDpoWrapper(DpoId dpoId, DTO item) {
        super(dpoId, item);
    }

    /**
     * Constructor.
     *
     * @param dpoId the item id
     */
    public CommonDpoWrapper(DpoId dpoId) {
        super(dpoId);
    }

    @Override
    public String toString() {
        return "AbstractDpoWrapper{" +
                "itemId=" + getItemId() +
                ", item=" + getItem() +
                '}';
    }
}