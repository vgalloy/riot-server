package vgalloy.riot.server.service.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.model.Position;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 18/08/16.
 */
public interface QueryService {

    /**
     * Get the winRate of a champion as a map where the key is the number of game played.
     *
     * @param championId the champion id
     * @return the winRate
     */
    Map<Integer, Double> getWinRate(int championId);

    /**
     * Get the position of a summoner during all game played with the given champion.
     *
     * @param summonerId the summoner id
     * @param championId the champion id
     * @return a list with all the game position. Each game is defined as a list of position
     */
    List<List<Position>> getPosition(long summonerId, int championId);

    /**
     * Get the champion win rate during the given period.
     *
     * @param championId the champion id
     * @param startDate  the start date (included)
     * @param endDate    the end date (excluded)
     * @return the win rate of the champion. Each entry is given with a timestamp.
     */
    Map<LocalDate, WinRate> getWinRate(int championId, LocalDate startDate, LocalDate endDate);
}
