package vgalloy.riot.server.dao.api.entity.dpoid;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 12/03/17.
 *
 * @author Vincent Galloy
 */
public final class CommonDpoId extends AbstractDpoId {

    private static final long serialVersionUID = 5822798644418867296L;

    /**
     * Constructor.
     *
     * @param region the region
     * @param id     the id
     */
    public CommonDpoId(Region region, Long id) {
        super(region, id);
    }
}
