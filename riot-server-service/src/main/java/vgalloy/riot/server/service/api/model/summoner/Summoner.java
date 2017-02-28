package vgalloy.riot.server.service.api.model.summoner;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class Summoner {

    private final SummonerId summonerId;
    private final String summonerName;
    private final Region region;
    private final Long summonerLevel;
    private final Integer profileIconId;

    /**
     * Constructor.
     *
     * @param summonerId    the summoner id
     * @param summonerName  the summoner name
     * @param summonerLevel the summoner level
     * @param profileIconId the profile icon id
     */
    public Summoner(SummonerId summonerId, String summonerName, Long summonerLevel, Integer profileIconId) {
        this.summonerId = Objects.requireNonNull(summonerId);
        this.summonerName = summonerName;
        region = summonerId.getRegion();
        this.summonerLevel = summonerLevel;
        this.profileIconId = profileIconId;
    }

    public SummonerId getSummonerId() {
        return summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Region getRegion() {
        return region;
    }

    public Long getSummonerLevel() {
        return summonerLevel;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }
}
