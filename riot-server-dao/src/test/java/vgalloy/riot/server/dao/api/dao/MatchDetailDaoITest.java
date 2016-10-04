package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version.Main;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public class MatchDetailDaoITest {

    private static final int PORT = 29001;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final MatchDetailDao matchDetailDao = MongoDaoFactory.getMatchDetailDao(URL + ":" + PORT);

    @BeforeClass
    public static void setUp() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        EXECUTABLE = starter.prepare(new MongodConfigBuilder()
                .version(Main.V3_2)
                .net(new Net(URL, PORT, false))
                .build());
        PROCESS = EXECUTABLE.start();
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }

    @Test
    public void testInsertWithCorrectSimpleMatchDetailDto() {
        // GIVEN
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(10);
        matchDetail.setRegion(Region.EUW);
        matchDetail.setMatchCreation(LocalDate.now().toEpochDay() * 24 * 3600 * 1000);

        // WHEN
        matchDetailDao.save(matchDetail);
        Optional<Entity<MatchDetail>> result = matchDetailDao.get(Region.EUW, 10L, LocalDate.now());

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
        player.setSummonerId(12345);
        player.setSummonerName("Name");

        ParticipantIdentity participantIdentity = new ParticipantIdentity();
        participantIdentity.setParticipantId(1);
        participantIdentity.setPlayer(player);

        List<ParticipantIdentity> participantIdentities = new ArrayList<>();
        participantIdentities.add(participantIdentity);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(234);
        matchDetail.setRegion(Region.EUW);
        matchDetail.setParticipantIdentities(participantIdentities);
        matchDetail.setMatchCreation(LocalDate.now().toEpochDay() * 24 * 3600 * 1000);

        // WHEN
        matchDetailDao.save(matchDetail);
        Optional<Entity<MatchDetail>> result = matchDetailDao.get(Region.EUW, 234L, LocalDate.now());

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

        MatchDetail matchDetail1 = createMatchDetail(Region.EUW, 10_001L, correctPlayerId);
        MatchDetail matchDetail2 = createMatchDetail(Region.EUW, 10_002L, correctPlayerId);
        MatchDetail matchDetail3 = createMatchDetail(Region.EUW, 10_003L, wrongPlayerId);

        // WHEN
        matchDetailDao.save(matchDetail1);
        matchDetailDao.save(matchDetail2);
        matchDetailDao.save(matchDetail3);

        // THEN
        // Wrong id
        List<MatchDetail> result = matchDetailDao.findMatchDetailBySummonerId(Region.BR, 105246, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Wrong region
        result = matchDetailDao.findMatchDetailBySummonerId(Region.BR, correctPlayerId, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());

        // Everything ok
        result = matchDetailDao.findMatchDetailBySummonerId(Region.EUW, correctPlayerId, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        Assert.assertEquals(2, result.size());

        // Wrong data time
        result = matchDetailDao.findMatchDetailBySummonerId(Region.EUW, correctPlayerId, LocalDate.now().plus(1, ChronoUnit.DAYS), LocalDate.now().plus(2, ChronoUnit.DAYS));
        Assert.assertEquals(0, result.size());
    }

    private MatchDetail createMatchDetail(Region region, long matchId, long summonerId) {
        Player player = new Player();
        player.setSummonerId(summonerId);

        ParticipantIdentity participantIdentity = new ParticipantIdentity();
        participantIdentity.setParticipantId(1);
        participantIdentity.setPlayer(player);

        List<ParticipantIdentity> participantIdentityList = new ArrayList<>();
        participantIdentityList.add(participantIdentity);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setParticipantIdentities(participantIdentityList);
        matchDetail.setMatchId(matchId);
        matchDetail.setRegion(region);
        matchDetail.setMatchCreation(LocalDate.now().toEpochDay() * 24 * 3600 * 1000);

        return matchDetail;
    }
}