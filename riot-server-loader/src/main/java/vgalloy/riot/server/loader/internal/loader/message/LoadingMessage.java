package vgalloy.riot.server.loader.internal.loader.message;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vincent Galloy on 13/10/16.
 * This class represents all the information for a loading demand. This class will be push in message broker.
 *
 * @author Vincent Galloy
 */
public final class LoadingMessage implements Serializable {

    private static final long serialVersionUID = 1138659658964486619L;

    private final LoaderType loaderType;
    private final String value;

    /**
     * Constructor.
     *
     * @param loaderType the {@link LoaderType}
     * @param value      the value
     */
    @JsonCreator
    public LoadingMessage(@JsonProperty("loaderType") LoaderType loaderType, @JsonProperty("value") String value) {
        this.loaderType = Objects.requireNonNull(loaderType);
        this.value = Objects.requireNonNull(value);
    }

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoadingMessage)) {
            return false;
        }
        LoadingMessage that = (LoadingMessage) o;
        return loaderType == that.loaderType &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loaderType, value);
    }

    @Override
    public String toString() {
        return "LoadingMessage{" +
            "loaderType=" + loaderType +
            ", value='" + value + '\'' +
            '}';
    }

    /**
     * The types of request.
     */
    public enum LoaderType {
        SUMMONER_BY_ID, SUMMONER_BY_NAME, ITEM_BY_ID, CHAMPION_BY_ID
    }
}
