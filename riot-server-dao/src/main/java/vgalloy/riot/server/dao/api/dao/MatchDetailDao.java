package vgalloy.riot.server.dao.api.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public interface MatchDetailDao {

    /**
     * Save the match detail.
     *
     * @param matchDetailWrapper the match detail wrapper
     */
    void save(MatchDetailWrapper matchDetailWrapper);

    /**
     * Get the match detail.
     *
     * @param matchDetailId the match detail id
     * @return the matchDetail
     */
    Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId);

    /**
     * Get all the games of a summoner during the period : [from, to[. The game ARE NOT sorted
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param from       the first day to analyse
     * @param to         the first to not analyse
     * @return the last games
     */
    List<MatchDetail> findMatchDetailBySummonerId(Region region, long summonerId, LocalDate from, LocalDate to);

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
