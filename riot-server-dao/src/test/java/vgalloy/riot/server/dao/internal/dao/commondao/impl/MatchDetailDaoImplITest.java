package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version.Main;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public class MatchDetailDaoImplITest {

    private static final int PORT = 29001;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final MatchDetailDaoImpl matchDetailDao = DaoFactory.getDao(MatchDetailDaoImpl.class, URL + ":" + PORT, "riotTest");

    @BeforeClass
    public static void setUp() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        EXECUTABLE = starter.prepare(new MongodConfigBuilder()
                .version(Main.PRODUCTION)
                .net(new Net(PORT, Network.localhostIsIPv6()))
                .build());
        PROCESS = EXECUTABLE.start();
    }

    @Test
    public void testInsertWithCorrectSimpleMatchDetailDto() {
        // GIVEN
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(10);

        // WHEN
        matchDetailDao.save(Region.EUW, 10L, matchDetail);
        Optional<Entity<MatchDetail>> result = matchDetailDao.get(Region.EUW, 10L);

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(matchDetail, result.get().getItem());
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
        matchDetail.setParticipantIdentities(participantIdentities);

        // WHEN
        matchDetailDao.save(Region.EUW, 234L, matchDetail);
        Optional<Entity<MatchDetail>> result = matchDetailDao.get(Region.EUW, 234L);

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(matchDetail, result.get().getItem());
    }

    @Test
    public void testLastGame() {
        // GIVEN
        long correctPlayerId = 12345;
        long wrongPlayerId = 12346;

        MatchDetail matchDetail1 = createMatchDetail(10_001L, correctPlayerId);
        MatchDetail matchDetail2 = createMatchDetail(10_002L, correctPlayerId);
        MatchDetail matchDetail3 = createMatchDetail(10_003L, wrongPlayerId);

        // WHEN
        matchDetailDao.save(Region.EUW, 10_001L, matchDetail1);
        matchDetailDao.save(Region.EUW, 10_002L, matchDetail2);
        matchDetailDao.save(Region.EUW, 10_003L, matchDetail3);

        // THEN
        List<MatchDetail> result = matchDetailDao.getLastMatchDetail(Region.BR, 105246, 10);
        assertEquals(0, result.size());

        result = matchDetailDao.getLastMatchDetail(Region.BR, correctPlayerId, 10);
        assertEquals(0, result.size());

        result = matchDetailDao.getLastMatchDetail(Region.EUW, correctPlayerId, 10);
        assertEquals(2, result.size());

        result = matchDetailDao.getLastMatchDetail(Region.EUW, correctPlayerId, 1);
        assertEquals(1, result.size());
    }

    private MatchDetail createMatchDetail(long matchId, long summonerId) {
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

        return matchDetail;
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }
}