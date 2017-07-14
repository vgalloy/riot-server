package com.vgalloy.riot.server.dao.api.entity.dpoid;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 12/03/17.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractDpoId implements DpoId {

    private static final long serialVersionUID = 6936153576810566295L;

    private final Region region;
    private final Long id;

    /**
     * Constructor.
     *
     * @param region the region
     * @param id     the id
     */
    AbstractDpoId(Region region, Long id) {
        this.region = Objects.requireNonNull(region);
        this.id = Objects.requireNonNull(id);
        if (id == 0L) {
            throw new IllegalArgumentException("id can not be equals to 0");
        }
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractDpoId that = (AbstractDpoId) o;
        return region == that.region &&
            Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, id);
    }
}
