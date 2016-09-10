package vgalloy.riot.server.dao.internal.query;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.api.api.dto.mach.ParticipantStats;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;
import vgalloy.riot.server.dao.internal.dao.factory.MongoClientFactory;

import static vgalloy.riot.server.dao.internal.query.WinRateQuery.getWinRate;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 10/09/16.
 */
public class WinRateQueryTest {

    private static final int PORT = 29006;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final MongoDatabase mongoDatabase = MongoClientFactory.get(URL + ":" + PORT).getDatabase("riotTest");
    private final MatchDetailDaoImpl matchDetailDao = DaoFactory.getDao(MatchDetailDaoImpl.class, URL + ":" + PORT, "riotTest");

    @BeforeClass
    public static void setUp() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        EXECUTABLE = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.V3_2)
                .net(new Net(PORT, Network.localhostIsIPv6()))
                .build());
        PROCESS = EXECUTABLE.start();
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }

    @Test
    public void testWinRateDuringOneDay() {
        // GIVEN
        int startDay = 17053;

        List<MatchDetail> input = new ArrayList<>();
        input.add(createMatchDetail(startDay * 24 * 3600 + 465, createParticipant(7, false), createParticipant(8, false)));
        input.add(createMatchDetail(startDay * 24 * 3600 + 123, createParticipant(7, false), createParticipant(9, false)));
        input.add(createMatchDetail(startDay * 24 * 3600 + 300, createParticipant(7, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail((startDay + 1) * 24 * 3600 + 300, createParticipant(7, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(startDay * 24 * 3600 - 300, createParticipant(7, true), createParticipant(8, true)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            matchDetailDao.save(Region.EUNE, (long) i + 1, input.get(i));
        }

        // THEN
        Assert.assertEquals(2, getWinRate(mongoDatabase, 7, LocalDate.ofEpochDay(startDay)).getLose());
        Assert.assertEquals(1, getWinRate(mongoDatabase, 7, LocalDate.ofEpochDay(startDay)).getWin());
    }

    @Test
    public void testWinRateDuringOneWeek() {
        // GIVEN
        int startDay = 17053;
        int endDay = 17053 + 7;

        List<MatchDetail> input = new ArrayList<>();
        input.add(createMatchDetail(startDay * 24 * 3600 + 465, createParticipant(7, false), createParticipant(8, false)));
        input.add(createMatchDetail(startDay * 24 * 3600 + 123, createParticipant(7, false), createParticipant(9, false)));
        input.add(createMatchDetail(startDay * 24 * 3600 + 300, createParticipant(7, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail(endDay * 24 * 3600 + 300, createParticipant(7, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(startDay * 24 * 3600 - 300, createParticipant(7, true), createParticipant(8, true)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            matchDetailDao.save(Region.EUNE, (long) i + 1, input.get(i));
        }

        // THEN
        Map<LocalDate, WinRate> result = WinRateQuery.getWinRate(mongoDatabase, 7, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay));
        Assert.assertEquals(7, result.size());
    }

    @Test
    public void testNoTime() {
        // GIVEN
        int startDay = 17053;
        int endDay = startDay;

        List<MatchDetail> input = new ArrayList<>();
        input.add(createMatchDetail(startDay * 24 * 3600 + 465, createParticipant(7, false), createParticipant(8, false)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            matchDetailDao.save(Region.EUNE, (long) i + 1, input.get(i));
        }

        // THEN
        Assert.assertEquals(0, WinRateQuery.getWinRate(mongoDatabase, 7, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay)).size());
    }

    /**
     * Convenient way to create simple MachDetail.
     *
     * @param creationDate the creation date
     * @param participants the participants
     * @return a fake MachDetail
     */
    private MatchDetail createMatchDetail(long creationDate, Participant... participants) {
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchCreation(creationDate);
        matchDetail.setParticipants(Arrays.asList(participants));
        return matchDetail;
    }

    /**
     * Convenient way to create simple Participant.
     *
     * @param championId the champion id
     * @param winner     the winner
     * @return a fake Participant
     */
    private Participant createParticipant(int championId, boolean winner) {
        Participant participant = new Participant();
        participant.setChampionId(championId);
        participant.setStats(new ParticipantStats());
        participant.getStats().setWinner(winner);
        return participant;
    }
}