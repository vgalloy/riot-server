package vgalloy.riot.server.service.api.model.game;

import java.util.Objects;

import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy - 09/12/16
 *         Created by Vincent Galloy on 09/12/16.
 */
public class SummonerInformation {

    private final SummonerId summonerId;
    private final String summonerName;
    private final Integer championId;

    /**
     * Constructor.
     *
     * @param summonerId   the summoner id
     * @param summonerName the summoner name
     * @param championId   the champion id played by the summoner
     */
    public SummonerInformation(SummonerId summonerId, String summonerName, int championId) {
        this.summonerId = Objects.requireNonNull(summonerId);
        this.summonerName = Objects.requireNonNull(summonerName);
        this.championId = Objects.requireNonNull(championId);
    }

    public SummonerId getSummonerId() {
        return summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Integer getChampionId() {
        return championId;
    }
}
