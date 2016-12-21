package vgalloy.riot.server.service.api.service;

import java.time.LocalDate;
import java.util.Map;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.WinRate;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/12/16.
 */
public interface ChampionService extends CommonService<ChampionDto> {

    /**
     * Get the win rate for all champion for the given day.
     *
     * @param date the day to analyse
     * @return a map (champion Id, win rate)
     */
    Map<Integer, WinRate> getWinRateForAllChampion(LocalDate date);
}
