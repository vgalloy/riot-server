package vgalloy.riot.server.dao.api.dao;

import java.util.List;
import java.util.Map;

import vgalloy.riot.server.dao.api.entity.Position;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public interface QueryDao {

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
}
