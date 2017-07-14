package com.vgalloy.riot.server.service.api.model.summoner;

import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.dto.stats.ChampionStatsDto;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class RankedStats {

    private final SummonerId summonerId;
    private final List<ChampionStatsDto> champions;
    private final Long modifyDate;

    /**
     * Constructor.
     *
     * @param summonerId the summoner id
     * @param champions  the champions
     * @param modifyDate the modify date
     */
    public RankedStats(SummonerId summonerId, List<ChampionStatsDto> champions, Long modifyDate) {
        this.summonerId = Objects.requireNonNull(summonerId);
        this.champions = Objects.requireNonNull(champions);
        this.modifyDate = Objects.requireNonNull(modifyDate);
    }

    public SummonerId getSummonerId() {
        return summonerId;
    }

    public List<ChampionStatsDto> getChampions() {
        return champions;
    }

    public Long getModifyDate() {
        return modifyDate;
    }
}
