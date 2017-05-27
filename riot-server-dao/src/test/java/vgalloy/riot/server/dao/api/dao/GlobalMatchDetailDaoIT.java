package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.internal.dao.TimelineDao;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.GlobalMatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.TimelineDaoImpl;

/**
 * Created by Vincent Galloy on 14/06/16.
 *
 * @author Vincent Galloy
 */
public final class GlobalMatchDetailDaoIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalMatchDetailDaoIT.class);
    private static final String URL = "localhost";
    private static final int PORT = 29502;

    private static MongodProcess process;
    private static MongodExecutable executable;

    private final MatchDetailDao matchDetailDao = new MatchDetailDaoImpl(URL + ":" + PORT, "riotTest");
    private final TimelineDao timelineDao = new TimelineDaoImpl(URL + ":" + PORT, "riotTest");
    private final MatchDetailDao dao = new GlobalMatchDetailDaoImpl(timelineDao, matchDetailDao);

    @BeforeClass
    public static void setUp() throws IOException {
        executable = DaoTestUtil.createMongodExecutable(LOGGER, URL, PORT);
        process = executable.start();
    }

    @AfterClass
    public static void tearDown() {
        process.stop();
        executable.stop();
    }

    private static MatchDetailWrapper createMatchDetail(Region region, Long id, LocalDateTime matchCreation, long summonerId) {
        MatchDetailId matchDetailId = new MatchDetailId(region, id, matchCreation.toLocalDate());

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
        matchDetail.setMatchCreation(matchCreation.toEpochSecond(ZoneOffset.UTC) * 1000);
        matchDetail.setTimeline(new Timeline());

        return new MatchDetailWrapper(matchDetailId, matchDetail);
    }

    @Test
    public void testInsertWithCorrectSimpleMatchDetailDto() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(10L);
        matchDetail.setRegion(Region.EUW);
        matchDetail.setMatchCreation(now.toEpochSecond(ZoneOffset.UTC) * 1000);

        // WHEN
        dao.save(new MatchDetailWrapper(new MatchDetailId(Region.EUW, 10L, now.toLocalDate()), matchDetail));
        Optional<Entity<MatchDetail, MatchDetailId>> result = dao.get(new MatchDetailId(Region.EUW, 10L, now.toLocalDate()));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(matchDetail, result.get().getItem().get());
        Assert.assertEquals(new MatchDetailId(Region.EUW, 10L, now.toLocalDate()), result.get().getItemId());
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
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        dao.save(createMatchDetail(Region.EUW, 10_001L, now, correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_002L, now, correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_003L, now.minus(1, ChronoUnit.DAYS), correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_004L, now, wrongPlayerId));

        // THEN
        // Wrong id
        List<MatchDetail> result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.EUW, 105246L), now, now.plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Wrong region
        result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.BR, correctPlayerId), now, now.plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Everything ok
        result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.EUW, correctPlayerId), now.minus(1, ChronoUnit.DAYS), now.plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(new Long(10_003), result.get(0).getMatchId());
        Assert.assertEquals(new Long(10_002), result.get(2).getMatchId());

        // Wrong data time
        result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.EUW, correctPlayerId), now.minus(2, ChronoUnit.DAYS), now.minus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testLastGameLimitCondition() {
        // GIVEN
        long correctPlayerId = 123456;
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        dao.save(createMatchDetail(Region.EUW, 11_001L, now.minus(5, ChronoUnit.MINUTES), correctPlayerId));

        // THEN
        List<MatchDetail> result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.EUW, correctPlayerId), now.minus(1, ChronoUnit.DAYS).plus(5, ChronoUnit.MINUTES), now);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testDeleteGameForADay() {
        // GIVEN
        long correctPlayerId = 12345;
        LocalDateTime now = LocalDateTime.now();

        // WHEN
        dao.save(createMatchDetail(Region.EUW, 10_001L, now, correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_002L, now, correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_003L, now.minus(1, ChronoUnit.DAYS), correctPlayerId));
        dao.save(createMatchDetail(Region.EUW, 10_004L, now, correctPlayerId));
        dao.cleanAllMatchForADay(now.toLocalDate());

        // THEN
        List<MatchDetail> result = dao.findMatchDetailBySummonerId(new CommonDpoId(Region.EUW, correctPlayerId), now, now.plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());
        Assert.assertFalse(timelineDao.get(new CommonDpoId(Region.EUW, 10_001L)).isPresent());
        Assert.assertTrue(timelineDao.get(new CommonDpoId(Region.EUW, 10_003L)).isPresent());
    }
}