package vgalloy.riot.server.dao.api.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public abstract class AbstractMatchDetailDaoITest {

    private final MatchDetailDao dao;

    protected AbstractMatchDetailDaoITest(MatchDetailDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Test
    public void testInsertWithCorrectSimpleMatchDetailDto() {
        // GIVEN
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(10L);
        matchDetail.setRegion(Region.EUW);
        matchDetail.setMatchCreation(LocalDate.now().toEpochDay() * 24 * 3600 * 1000);

        // WHEN
        dao.save(new MatchDetailWrapper(new MatchDetailId(Region.EUW, 10L, LocalDate.now()), matchDetail));
        Optional<Entity<MatchDetail, MatchDetailId>> result = dao.get(new MatchDetailId(Region.EUW, 10L, LocalDate.now()));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(matchDetail, result.get().getItem().get());
    }

    @Test
    public void testInsertWithCorrectComplexMatchDetailDto() {
        // GIVEN
        Player player = new Player();
        player.setSummonerId(12345L);
        player.setSummonerName("Name");

        ParticipantIdentity participantIdentity = new ParticipantIdentity();
        participantIdentity.setParticipantId(1);
        participantIdentity.setPlayer(player);

        List<ParticipantIdentity> participantIdentities = new ArrayList<>();
        participantIdentities.add(participantIdentity);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(234L);
        matchDetail.setRegion(Region.EUW);
        matchDetail.setParticipantIdentities(participantIdentities);
        matchDetail.setMatchCreation(LocalDate.now().toEpochDay() * 24 * 3600 * 1000);

        // WHEN
        dao.save(new MatchDetailWrapper(new MatchDetailId(Region.EUW, 234L, LocalDate.now()), matchDetail));
        Optional<Entity<MatchDetail, MatchDetailId>> result = dao.get(new MatchDetailId(Region.EUW, 234L, LocalDate.now()));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(matchDetail, result.get().getItem().get());
    }

    @Test
    public void testLastGame() {
        // GIVEN
        long correctPlayerId = 12345;
        long wrongPlayerId = 12346;

        // WHEN
        dao.save(createMatchDetail(new MatchDetailId(Region.EUW, 10_001L, LocalDate.now()), correctPlayerId));
        dao.save(createMatchDetail(new MatchDetailId(Region.EUW, 10_002L, LocalDate.now()), correctPlayerId));
        dao.save(createMatchDetail(new MatchDetailId(Region.EUW, 10_003L, LocalDate.now()), wrongPlayerId));

        // THEN
        // Wrong id
        List<MatchDetail> result = dao.findMatchDetailBySummonerId(Region.BR, 105246, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Wrong region
        result = dao.findMatchDetailBySummonerId(Region.BR, correctPlayerId, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Everything ok
        result = dao.findMatchDetailBySummonerId(Region.EUW, correctPlayerId, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(2, result.size());

        // Wrong data time
        result = dao.findMatchDetailBySummonerId(Region.EUW, correctPlayerId, LocalDate.now().plus(1, ChronoUnit.DAYS), LocalDate.now().plus(2, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());
    }

    private MatchDetailWrapper createMatchDetail(MatchDetailId matchDetailId, long summonerId) {
        Player player = new Player();
        player.setSummonerId(summonerId);

        ParticipantIdentity participantIdentity = new ParticipantIdentity();
        participantIdentity.setParticipantId(1);
        participantIdentity.setPlayer(player);

        List<ParticipantIdentity> participantIdentityList = new ArrayList<>();
        participantIdentityList.add(participantIdentity);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setParticipantIdentities(participantIdentityList);
        matchDetail.setMatchId(matchDetailId.getId());
        matchDetail.setRegion(matchDetailId.getRegion());
        matchDetail.setMatchCreation(matchDetailId.getMatchDate().toEpochDay() * 24 * 3600 * 1000);

        return new MatchDetailWrapper(matchDetailId, matchDetail);
    }
}