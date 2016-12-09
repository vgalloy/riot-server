package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 09/12/16
 *         Created by Vincent Galloy on 09/12/16.
 */
public class SummonerInformation implements Serializable {

    private static final long serialVersionUID = 7255871201437606776L;

    private final Region region;
    private final Long summonerId;
    private final String summonerName;
    private final Integer championId;

    /**
     * Constructor.
     *
     * @param region       the region
     * @param summonerId   the summoner id
     * @param summonerName the summoner name
     * @param championId   the champion id played by the summoner
     */
    public SummonerInformation(Region region, long summonerId, String summonerName, int championId) {
        this.region = Objects.requireNonNull(region);
        this.summonerId = Objects.requireNonNull(summonerId);
        this.summonerName = Objects.requireNonNull(summonerName);
        this.championId = Objects.requireNonNull(championId);
    }

    public Region getRegion() {
        return region;
    }

    public Long getSummonerId() {
        return summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Integer getChampionId() {
        return championId;
    }
}
