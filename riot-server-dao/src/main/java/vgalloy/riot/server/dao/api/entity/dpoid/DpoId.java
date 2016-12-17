package vgalloy.riot.server.dao.api.entity.dpoid;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class DpoId implements Serializable {

    private static final long serialVersionUID = 6936153576810566295L;

    private final Region region;
    private final Long id;

    /**
     * Constructor.
     *
     * @param region the region
     * @param id     the id
     */
    public DpoId(Region region, Long id) {
        this.region = Objects.requireNonNull(region);
        this.id = Objects.requireNonNull(id);
        if (id == 0L) {
            throw new IllegalArgumentException("id can not be equals to 0");
        }
    }

    public Region getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DpoId{" +
                "region=" + region +
                ", id=" + id +
                '}';
    }
}
