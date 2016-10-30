package vgalloy.riot.server.loader.internal.loader.message;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vincent Galloy - 13/10/16
 *         Created by Vincent Galloy on 13/10/16.
 */
public class SummonerLoadingMessage implements Serializable {

    private static final long serialVersionUID = 1138659658964486619L;

    private final LoaderType loaderType;
    private final String summonerName;
    private final Long summonerId;

    /**
     * Constructor.
     *
     * @param loaderType   the {@link LoaderType}
     * @param summonerName the summoner name
     * @param summonerId   the summoner id
     */
    @JsonCreator
    private SummonerLoadingMessage(@JsonProperty("loaderType") LoaderType loaderType, @JsonProperty("summonerName") String summonerName, @JsonProperty("summonerId") Long summonerId) {
        this.loaderType = Objects.requireNonNull(loaderType);
        this.summonerName = summonerName;
        this.summonerId = summonerId;
    }

    /**
     * Build a SummonerLoadingMessage with summonerId.
     *
     * @param summonerId the summoner id
     * @return the SummonerLoadingMessage
     */
    public static SummonerLoadingMessage byId(Long summonerId) {
        return new SummonerLoadingMessage(LoaderType.BY_ID, null, Objects.requireNonNull(summonerId));
    }

    /**
     * Build a SummonerLoadingMessage with summonerId.
     *
     * @param summonerName the summoner name
     * @return the SummonerLoadingMessage
     */
    public static SummonerLoadingMessage byName(String summonerName) {
        return new SummonerLoadingMessage(LoaderType.BY_NAME, Objects.requireNonNull(summonerName), null);
    }

    public LoaderType getLoaderType() {
        return loaderType;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SummonerLoadingMessage)) {
            return false;
        }
        SummonerLoadingMessage that = (SummonerLoadingMessage) o;
        return loaderType == that.loaderType &&
                Objects.equals(summonerName, that.summonerName) &&
                Objects.equals(summonerId, that.summonerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loaderType, summonerName, summonerId);
    }

    @Override
    public String toString() {
        return "SummonerLoadingMessage{" +
                "loaderType=" + loaderType +
                ", summonerName='" + summonerName + '\'' +
                ", summonerId=" + summonerId +
                '}';
    }

    public enum LoaderType {
        BY_ID, BY_NAME
    }
}
