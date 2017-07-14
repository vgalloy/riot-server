package com.vgalloy.riot.server.service.api.model.summoner;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerId {

    private final Region region;
    private final Long id;

    /**
     * The summoner id.
     *
     * @param region the region
     * @param id     the id
     */
    public SummonerId(Region region, Long id) {
        this.region = Objects.requireNonNull(region);
        this.id = Objects.requireNonNull(id);
    }

    public Region getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SummonerId)) {
            return false;
        }
        SummonerId that = (SummonerId) o;
        return region == that.region &&
            Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, id);
    }

    @Override
    public String toString() {
        return "SummonerId{" +
            "region=" + region +
            ", id=" + id +
            '}';
    }
}
