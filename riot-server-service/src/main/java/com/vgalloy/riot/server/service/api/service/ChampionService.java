package com.vgalloy.riot.server.service.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.dao.api.entity.WinRate;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * Created by Vincent Galloy on 08/12/16.
 *
 * @author Vincent Galloy
 */
public interface ChampionService {

    /**
     * Get the ChampionDto.
     *
     * @param dpoId the champion id
     * @return the champion dto
     */
    ResourceWrapper<ChampionDto> get(DpoId dpoId);

    /**
     * Get the win rate for all champion for the given day.
     *
     * @param date the day to analyse
     * @return a map (champion Id, win rate)
     */
    Map<Integer, WinRate> getWinRateForAllChampion(LocalDate date);

    /**
     * Get the winRate of a champion as a map where the key is the number of game played.
     *
     * @param championId the champion id
     * @return the winRate
     */
    Map<Integer, Double> getWinRateByGamePlayed(int championId);

    /**
     * Get the champion win rate during the given period.
     *
     * @param championId the champion id
     * @param startDate  the start date (included)
     * @param endDate    the end date (excluded)
     * @return the win rate of the champion. Each entry is given with a timestamp.
     */
    Map<LocalDate, WinRate> getWinRateDuringPeriodOfTime(int championId, LocalDate startDate, LocalDate endDate);

    /**
     * Get all {@link ChampionName} where name start with the given name.
     *
     * @param region       the region
     * @param championName the champion name
     * @return a list of champion name
     */
    List<ChampionName> autoCompleteChampionName(Region region, String championName);
}
