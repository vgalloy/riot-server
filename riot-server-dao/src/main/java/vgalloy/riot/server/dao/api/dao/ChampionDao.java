package vgalloy.riot.server.dao.api.dao;

import java.time.LocalDate;
import java.util.Map;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.WinRate;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public interface ChampionDao extends CommonDao<ChampionDto> {

    /**
     * Get the winRate of a champion as a mapToEntity where the key is the number of game played.
     *
     * @param championId the champion id
     * @return the winRate
     */
    Map<Integer, Double> getWinRate(int championId);

    /**
     * Get the champion win rate during the given period : [from, to[.
     *
     * @param championId the champion id
     * @param from       the first day to analyse
     * @param to         the first to not analyse
     * @return the win rate of the champion during the given period
     */
    Map<LocalDate, WinRate> getWinRate(int championId, LocalDate from, LocalDate to);

    /**
     * Get the champion win rate during the given day.
     *
     * @param championId the champion id
     * @param localDate  the day to analyse
     * @return the win rate of the champion during the day
     */
    WinRate getWinRate(int championId, LocalDate localDate);
}
