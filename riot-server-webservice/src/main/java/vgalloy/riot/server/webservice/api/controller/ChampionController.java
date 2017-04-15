package vgalloy.riot.server.webservice.api.controller;

import java.util.Map;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.WinRate;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
public interface ChampionController {

    /**
     * Get champion information.
     *
     * @param championId the champion id
     * @param region     the region for the champion information (default EUW)
     * @return the champion information
     */
    ChampionDto getChampion(Long championId, Region region);

    /**
     * Get the win rate of a champion as a map. The key is the number of game played.
     *
     * @param championId the champion id
     * @return the win rates as a map
     */
    Map<Integer, Double> getWinRateByGamePlayed(Integer championId);

    /**
     * Get the win rate of a champion as a map. The key is the number of game played.
     *
     * @param championId the champion id
     * @param fromDay    the start search date in day
     * @param toDay      the end search date in day
     * @return the win rates as a map
     */
    Map<Long, WinRate> getWinRateDuringPeriodOfTime(Integer championId, Long fromDay, Long toDay);

    /**
     * Get the win rate for all champion for the given day.
     *
     * @param day the day to analyse
     * @return a map (champion Id, win rate)
     */
    Map<Integer, WinRate> getWinRateForAllChampion(Long day);
}
