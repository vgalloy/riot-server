package vgalloy.riot.server.dao.internal.query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import vgalloy.riot.server.dao.internal.dao.impl.ChampionDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.GlobalMatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.TimelineDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 10/09/16.
 */
public class WinRateQueryITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WinRateQueryITest.class);
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

    @Test
    public void testWinRateDuringOneDay() {
        // GIVEN
        long startDay = LocalDate.now().toEpochDay();

        List<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(7, false), createParticipant(8, false)));
        input.add(createMatchDetail(2, Region.EUNE, startDay * 24 * 3600 * 1000 + 123, createParticipant(7, false), createParticipant(9, false)));
        input.add(createMatchDetail(3, Region.EUNE, startDay * 24 * 3600 * 1000 + 300, createParticipant(7, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail(4, Region.EUNE, (startDay + 1) * 24 * 3600 * 1000 + 300, createParticipant(7, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(5, Region.EUNE, startDay * 24 * 3600 * 1000 - 300, createParticipant(7, true), createParticipant(8, true)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            matchDetailDao.save(input.get(i));
        }

        // THEN
        Assert.assertEquals(2, dao.getWinRate(7, LocalDate.ofEpochDay(startDay)).getLose());
        Assert.assertEquals(1, dao.getWinRate(7, LocalDate.ofEpochDay(startDay)).getWin());
    }

    @Test
    public void testWinRateDuringOneWeek() {
        // GIVEN
        long startDay = LocalDate.now().minus(7, ChronoUnit.DAYS).toEpochDay();
        long endDay = startDay + 7;

        List<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(17, false), createParticipant(8, false)));
        input.add(createMatchDetail(2, Region.EUNE, startDay * 24 * 3600 * 1000 + 123, createParticipant(17, false), createParticipant(9, false)));
        input.add(createMatchDetail(3, Region.EUNE, startDay * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(8, true)));

        /* to late */
        input.add(createMatchDetail(4, Region.EUNE, endDay * 24 * 3600 * 1000 + 300, createParticipant(17, true), createParticipant(8, true)));
        /* to early */
        input.add(createMatchDetail(5, Region.EUNE, startDay * 24 * 3600 * 1000 - 300, createParticipant(17, true), createParticipant(8, true)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            globalMatchDetailDao.save(input.get(i));
        }

        // THEN
        Map<LocalDate, WinRate> result = dao.getWinRate(17, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay));
        Assert.assertEquals(7, result.size());
    }

    @Test
    public void testNoTime() {
        // GIVEN
        long startDay = LocalDate.now().toEpochDay();
        long endDay = startDay;

        List<MatchDetailWrapper> input = new ArrayList<>();
        input.add(createMatchDetail(1, Region.EUNE, startDay * 24 * 3600 * 1000 + 465, createParticipant(27, false), createParticipant(8, false)));

        // WHEN
        for (int i = 0; i < input.size(); i++) {
            globalMatchDetailDao.save(input.get(i));
        }

        // THEN
        Assert.assertEquals(0, dao.getWinRate(27, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay)).size());
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
    private MatchDetailWrapper createMatchDetail(long id, Region region, long creationDate, Participant... participants) {
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
    private Participant createParticipant(int championId, boolean winner) {
        Participant participant = new Participant();
        participant.setChampionId(championId);
        participant.setStats(new ParticipantStats());
        participant.getStats().setWinner(winner);
        return participant;
    }
}