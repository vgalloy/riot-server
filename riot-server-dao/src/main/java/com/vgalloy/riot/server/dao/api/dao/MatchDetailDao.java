package com.vgalloy.riot.server.dao.api.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import vgalloy.riot.api.api.dto.mach.MatchDetail;

import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import com.vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;

/**
 * Created by Vincent Galloy on 28/08/16.
 *
 * @author Vincent Galloy
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
     * Get all the games of a summoner during the period : [from, to[. The games are sorted by date (ascending).
     *
     * @param summonerId the summoner id
     * @param from       the first day to analyse
     * @param to         the first to not analyse
     * @return the last games
     */
    List<MatchDetail> findMatchDetailBySummonerId(DpoId summonerId, LocalDateTime from, LocalDateTime to);

    /**
     * Remove all the match and timeline for a given day.
     *
     * @param localDate the day to clean
     * @return the list of deleted match detail ids
     */
    List<MatchDetailId> cleanAllMatchForADay(LocalDate localDate);

    /**
     * Remove the match detail and timeline.
     *
     * @param matchDetailId the match detail id
     */
    void remove(MatchDetailId matchDetailId);
}
