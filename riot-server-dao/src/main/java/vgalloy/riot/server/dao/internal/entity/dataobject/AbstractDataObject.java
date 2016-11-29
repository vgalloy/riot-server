package vgalloy.riot.server.dao.internal.entity.dataobject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.mongojack.Id;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.internal.entity.Key;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
public abstract class AbstractDataObject<DTO> {

    private final Long lastUpdate;
    private final Region region;
    private final Long itemId;
    private DTO item;
    private final Key key;

    /**
     * Constructor.
     * @param lastUpdate the last update time in second (UTC)
     * @param region the region of the item
     * @param itemId the item id
     * @param item the item
     * @param id the id
     */
    public AbstractDataObject(Long lastUpdate, Region region, Long itemId, DTO item, String id) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
        this.region = Objects.requireNonNull(region);
        this.itemId = Objects.requireNonNull(itemId);
        this.item = item;
        key = Key.fromNormalizedString(id);
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public AbstractDataObject(Region region, Long itemId) {
        lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.region = Objects.requireNonNull(region);
        this.itemId = Objects.requireNonNull(itemId);
        key = new Key(region, itemId);
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public Region getRegion() {
        return region;
    }

    public Long getItemId() {
        return itemId;
    }

    public DTO getItem() {
        return item;
    }

    public void setItem(DTO item) {
        this.item = item;
    }

    @Id
    public String getId() {
        return key.normalizeString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDataObject)) {
            return false;
        }
        AbstractDataObject<?> that = (AbstractDataObject<?>) o;
        return Objects.equals(lastUpdate, that.lastUpdate) &&
                region == that.region &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(item, that.item) &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastUpdate, region, itemId, item, key);
    }

    @Override
    public String toString() {
        return "AbstractDataObject{" +
                "lastUpdate=" + lastUpdate +
                ", region=" + region +
                ", ItemId=" + itemId +
                ", item=" + item +
                ", key=" + key +
                '}';
    }
}
