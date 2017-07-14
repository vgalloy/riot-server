package com.vgalloy.riot.server.dao.api.entity.wrapper;

import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * Created by Vincent Galloy on 07/10/16.
 *
 * @author Vincent Galloy
 */
public final class CommonDpoWrapper<DTO> extends AbstractDpoWrapper<DTO, DpoId> {

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

    @Override
    public String toString() {
        return "AbstractDpoWrapper{" +
            "itemId=" + getItemId() +
            ", item=" + getItem() +
            '}';
    }
}
