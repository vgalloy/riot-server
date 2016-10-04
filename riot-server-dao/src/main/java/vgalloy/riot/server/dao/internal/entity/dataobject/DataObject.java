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
public abstract class DataObject<DTO> {

    private Long lastUpdate;
    private Region region;
    private Long itemId;
    private DTO item;
    private Key key;

    /**
     * Constructor. For Jackson deserialization.
     */
    protected DataObject() {

    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     */
    public DataObject(Region region, Long itemId) {
        lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.region = Objects.requireNonNull(region, "region can not be null");
        this.itemId = Objects.requireNonNull(itemId, "itemId can not be null");
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

    public void setId(String id) {
        key = Key.fromNormalizedString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataObject)) {
            return false;
        }
        DataObject<?> that = (DataObject<?>) o;
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
        return "DataObject{" +
                "lastUpdate=" + lastUpdate +
                ", region=" + region +
                ", itemId=" + itemId +
                ", item=" + item +
                ", key=" + key +
                '}';
    }
}
