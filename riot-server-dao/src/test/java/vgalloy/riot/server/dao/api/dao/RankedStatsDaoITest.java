package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.ArrayList;
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
import vgalloy.riot.api.api.dto.stats.ChampionStatsDto;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.dao.impl.RankedStatsDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public class RankedStatsDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29504;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RankedStatsDao rankedStatsDao = new RankedStatsDaoImpl(URL + ":" + PORT, "riotTest");

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

    @Test(expected = NullPointerException.class)
    public void testNullRegion() {
        rankedStatsDao.get(new DpoId(null, 1L));
    }

    @Test
    public void testEmptyDatabase() {
        Optional<Entity<RankedStatsDto, DpoId>> rankedStatsEntity = rankedStatsDao.get(new DpoId(Region.BR, 1L));
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testRandomWithNullRegion() {
        rankedStatsDao.getRandom(null);
    }

    @Test
    public void testEmptyRandom() {
        Optional<Entity<RankedStatsDto, DpoId>> rankedStatsEntity = rankedStatsDao.getRandom(Region.KR);
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullRankedStatsDto() {
        rankedStatsDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 1L), null));
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullId() {
        rankedStatsDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, null), new RankedStatsDto()));
    }

    @Test
    public void testInsertWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(10L);

        // WHEN
        rankedStatsDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 10L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = rankedStatsDao.get(new DpoId(Region.EUW, 10L));

        // THEN
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem().get());
    }

    @Test
    public void testRandomWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(11L);

        // WHEN
        rankedStatsDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 11L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = rankedStatsDao.getRandom(Region.EUW);

        // THEN
        Assert.assertNotNull(result);
    }

    @Test
    public void testCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setChampions(new ArrayList<>());
        rankedStatsDto.getChampions().add(new ChampionStatsDto());
        rankedStatsDto.getChampions().get(0).setId(10);
        rankedStatsDto.getChampions().add(new ChampionStatsDto());
        rankedStatsDto.setSummonerId(12L);

        // WHEN
        rankedStatsDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 12L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = rankedStatsDao.get(new DpoId(Region.EUW, 12L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem().get());
    }
}