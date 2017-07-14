package com.vgalloy.riot.server.dao.internal.entity.dpo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongojack.Id;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.dao.internal.entity.mapper.DpoIdMapper;

/**
 * Created by Vincent Galloy on 12/07/16.
 *
 * @author Vincent Galloy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
public abstract class AbstractDpo<DTO> {

    private final Long lastUpdate;
    private final DpoId dpoId;
    private DTO item;

    /**
     * Constructor.
     *
     * @param lastUpdate the last update time in second (UTC)
     * @param region     the region of the item
     * @param itemId     the item id
     * @param item       the item
     */
    public AbstractDpo(Long lastUpdate, Region region, Long itemId, DTO item) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
        dpoId = new CommonDpoId(region, itemId);
        this.item = item;
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public AbstractDpo(Region region, Long itemId) {
        lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        dpoId = new CommonDpoId(region, itemId);
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public Region getRegion() {
        return dpoId.getRegion();
    }

    public Long getItemId() {
        return dpoId.getId();
    }

    public DTO getItem() {
        return item;
    }

    public void setItem(DTO item) {
        this.item = item;
    }

    @Id
    public String getId() {
        return DpoIdMapper.toNormalizedString(dpoId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractDpo<?> that = (AbstractDpo<?>) o;
        return Objects.equals(lastUpdate, that.lastUpdate) &&
            Objects.equals(dpoId, that.dpoId) &&
            Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastUpdate, dpoId, item);
    }

    @Override
    public String toString() {
        return "AbstractDpo{" +
            "lastUpdate=" + lastUpdate +
            ", dpoId=" + dpoId +
            ", item=" + item +
            '}';
    }
}
