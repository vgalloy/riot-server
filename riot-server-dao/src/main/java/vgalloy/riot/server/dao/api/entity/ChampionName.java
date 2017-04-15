package vgalloy.riot.server.dao.api.entity;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;

/**
 * Created by Vincent Galloy on 15/04/17.
 *
 * @author Vincent Galloy
 */
public final class ChampionName {

    private final CommonDpoId commonDpoId;
    private final String championName;

    /**
     * Constructor.
     *
     * @param commonDpoId  the champion id
     * @param championName the champion name
     */
    public ChampionName(CommonDpoId commonDpoId, String championName) {
        this.commonDpoId = Objects.requireNonNull(commonDpoId);
        this.championName = Objects.requireNonNull(championName);
    }

    public CommonDpoId getCommonDpoId() {
        return commonDpoId;
    }

    public String getChampionName() {
        return championName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChampionName that = (ChampionName) o;
        return Objects.equals(commonDpoId, that.commonDpoId) &&
            Objects.equals(championName, that.championName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commonDpoId, championName);
    }

    @Override
    public String toString() {
        return "ChampionName{" +
            "commonDpoId=" + commonDpoId +
            ", championName='" + championName + '\'' +
            '}';
    }
}
