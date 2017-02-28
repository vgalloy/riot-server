package vgalloy.riot.server.dao.internal.query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.api.api.dto.mach.ParticipantStats;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.internal.dao.TimelineDao;
import vgalloy.riot.server.dao.internal.dao.impl.champion.ChampionDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.GlobalMatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.TimelineDaoImpl;

/**
 * Created by Vincent Galloy on 10/09/16.
 *
 * @author Vincent Galloy
 */
public class DatabaseCleanerHelperITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleanerHelperITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29702;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final MatchDetailDao matchDetailDao = new MatchDetailDaoImpl(URL + ":" + PORT, "riotTest");
    private final TimelineDao timelineDao = new TimelineDaoImpl(URL + ":" + PORT, "riotTest");
    private final MatchDetailDao globalMatchDetailDao = new GlobalMatchDetailDaoImpl(timelineDao, matchDetailDao);
    private final ChampionDao dao = new ChampionDaoImpl(URL + ":" + PORT, "riotTest");

    @BeforeClass
    public static void setUp() throws IOException {
        EXECUTABLE = DaoTestUtil.createMongodExecutable(LOGGER, URL, PORT);
        PROCESS = EXECUTABLE.start();
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }

    /**
     * Convenient way to create simple MachDetail.
     *
     * @param id           the match id
     * @param region       the match region
     * @param creationDate the creation date
     * @param participants the participants
     * @return a fake MachDetail
     */
    private static MatchDetailWrapper createMatchDetail(long id, Region region, long creationDate, Participant... participants) {
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(id);
        matchDetail.setRegion(region);
        matchDetail.setMatchCreation(creationDate);
        matchDetail.setParticipants(Arrays.asList(participants));

        return new MatchDetailWrapper(new MatchDetailId(region, id, LocalDate.ofEpochDay(creationDate / 1000 / 3600 / 24)), matchDetail);
    }

    /**
     * Convenient way to create simple Participant.
     *
     * @param championId the champion id
     * @param winner     the winner
     * @return a fake Participant
     */
    private static Participant createParticipant(int championId, boolean winner) {
        Participant participant = new Participant();
        participant.setChampionId(championId);
        participant.setStats(new ParticipantStats());
        participant.getStats().setWinner(winner);
        return participant;
    }

    @Test
    public void testWinRateDuringOneDay() {
        // GIVEN
        long startDay = LocalDate.now().toEpochDay();

        Collection<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(7, false), createParticipant(8, false)));
        input.add(createMatchDetail(2, Region.EUNE, startDay * 24 * 3600 * 1000 + 123, createParticipant(7, false), createParticipant(9, false)));
        input.add(createMatchDetail(3, Region.EUNE, startDay * 24 * 3600 * 1000 + 300, createParticipant(7, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail(4, Region.EUNE, (startDay + 1) * 24 * 3600 * 1000 + 300, createParticipant(7, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(5, Region.EUNE, startDay * 24 * 3600 * 1000 - 300, createParticipant(7, true), createParticipant(8, true)));

        // WHEN
        input.forEach(globalMatchDetailDao::save);

        // THEN
        Assert.assertEquals(2, dao.getWinRate(7, LocalDate.ofEpochDay(startDay)).getLose());
        Assert.assertEquals(1, dao.getWinRate(7, LocalDate.ofEpochDay(startDay)).getWin());
    }

    @Test
    public void testWinRateDuringOneWeek() {
        // GIVEN
        long startDay = LocalDate.now().minus(7, ChronoUnit.DAYS).toEpochDay();
        long endDay = startDay + 7;

        Collection<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(17, false), createParticipant(8, false)));
        input.add(createMatchDetail(2, Region.EUNE, startDay * 24 * 3600 * 1000 + 123, createParticipant(17, false), createParticipant(9, false)));
        input.add(createMatchDetail(3, Region.EUNE, startDay * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail(4, Region.EUNE, endDay * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(5, Region.EUNE, startDay * 24 * 3600 * 1000 - 300, createParticipant(17, true), createParticipant(8, true)));

        // WHEN
        input.forEach(globalMatchDetailDao::save);

        // THEN
        Map<LocalDate, WinRate> result = dao.getWinRateDuringPeriodOfTime(17, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay));
        Assert.assertEquals(7, result.size());
    }

    @Test
    public void testNoTime() {
        // GIVEN
        long startDay = LocalDate.now().toEpochDay();
        long endDay = startDay;

        Collection<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(27, false), createParticipant(8, false)));

        // WHEN
        input.forEach(globalMatchDetailDao::save);

        // THEN
        Assert.assertEquals(0, dao.getWinRateDuringPeriodOfTime(27, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay)).size());
    }

    @Test
    public void testWinRateForAllChampion() {
        // GIVEN
        LocalDate date = LocalDate.now();

        Collection<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, date.toEpochDay() * 24 * 3600 * 1000 + 465, createParticipant(17, false), createParticipant(8, false)));
        input.add(createMatchDetail(2, Region.EUNE, date.toEpochDay() * 24 * 3600 * 1000 + 123, createParticipant(17, false), createParticipant(9, false)));
        input.add(createMatchDetail(3, Region.EUNE, date.toEpochDay() * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(8, true)));
        input.add(createMatchDetail(4, Region.EUNE, date.toEpochDay() * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(6, true)));
        input.add(createMatchDetail(5, Region.EUNE, date.toEpochDay() * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(6, true)));

        // WHEN
        input.forEach(globalMatchDetailDao::save);

        // THEN
        Map<Integer, WinRate> result = dao.getWinRateForAllChampion(date);
        Assert.assertEquals(new WinRate(3, 2), result.get(17));
        Assert.assertEquals(new WinRate(1, 1), result.get(8));
        Assert.assertEquals(new WinRate(0, 1), result.get(9));
        Assert.assertEquals(new WinRate(2, 0), result.get(6));
    }
}