package com.vgalloy.riot.server.dao.api.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.dao.api.entity.WinRate;

/**
 * Created by Vincent Galloy on 28/08/16.
 *
 * @author Vincent Galloy
 */
public interface ChampionDao extends CommonDao<ChampionDto> {

    /**
     * Get the winRate of a champion as a map where the key is the number of game played.
     *
     * @param championId the champion id
     * @return the winRate
     */
    Map<Integer, Double> getWinRateByGamePlayed(int championId);

    /**
     * Get the champion win rate during the given period : [from, to[.
     *
     * @param championId the champion id
     * @param from       the first day to analyse
     * @param to         the first to not analyse
     * @return the win rate of the champion during the given period
     */
    Map<LocalDate, WinRate> getWinRateDuringPeriodOfTime(int championId, LocalDate from, LocalDate to);

    /**
     * Get the champion win rate during the given day.
     *
     * @param championId the champion id
     * @param localDate  the day to analyse
     * @return the win rate of the champion during the day
     */
    WinRate getWinRate(int championId, LocalDate localDate);

    /**
     * Get the win rate for all champion for the given day.
     *
     * @param date the day to analyse
     * @return a map (champion Id, win rate)
     */
    Map<Integer, WinRate> getWinRateForAllChampion(LocalDate date);

    /**
     * Get all {@link ChampionDto} where name start with the given name.
     *
     * @param region       the region
     * @param championName the champion name
     * @param limit        the max number of champion fetched
     * @return a list of champion
     */
    List<ChampionName> autoCompleteChampionName(Region region, String championName, int limit);
}
