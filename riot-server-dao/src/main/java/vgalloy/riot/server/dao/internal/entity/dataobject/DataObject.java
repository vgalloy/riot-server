package vgalloy.riot.server.dao.internal.entity.dataobject;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mongojack.Id;

import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.server.dao.internal.entity.Key;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
public abstract class DataObject<DTO> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Class<DTO> clazz;

    private Long lastUpdate;
    private Region region;
    private Long itemId;
    private DTO item;
    private Key key;

    /**
     * Constructor. For Jackson deserialization.
     */
    protected DataObject() {
        clazz = (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     * @param item   the item
     */
    public DataObject(Region region, Long itemId, DTO item) {
        this();
        lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.region = Objects.requireNonNull(region, "region can not be null");
        this.itemId = Objects.requireNonNull(itemId, "itemId can not be null");
        this.item = Objects.requireNonNull(item, "item can not be null");
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

    /**
     * @param item the item
     * @throws Exception the exception
     */
    public void setItem(DTO item) throws Exception {
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
