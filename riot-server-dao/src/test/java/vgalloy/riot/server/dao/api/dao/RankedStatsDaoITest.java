package vgalloy.riot.server.dao.api.dao;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version.Main;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.ChampionStatsDto;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.GenericDaoImpl;
import vgalloy.riot.server.dao.internal.entity.Key;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public class RankedStatsDaoITest {

    private static final int PORT = 29002;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RankedStatsDao rankedStatsDao = MongoDaoFactory.getRankedStatsDao(URL + ":" + PORT);

    @BeforeClass
    public static void setUp() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        EXECUTABLE = starter.prepare(new MongodConfigBuilder()
                .version(Main.V3_2)
                .net(new Net(URL, PORT, Network.localhostIsIPv6()))
                .build());
        PROCESS = EXECUTABLE.start();
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }

    @Test
    public void testNullRegion() {
        try {
            rankedStatsDao.get(null, 1L);
            Assert.fail("No exception");
        } catch (Exception e) {
            Assert.assertSame(NullPointerException.class, e.getClass());
            Assert.assertEquals(Key.REGION_CAN_NOT_BE_NULL, e.getMessage());
        }
    }

    @Test
    public void testEmptyDatabase() {
        Optional<Entity<RankedStatsDto>> rankedStatsEntity = rankedStatsDao.get(Region.BR, 1L);
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test
    public void testRandomWithNullRegion() {
        try {
            rankedStatsDao.getRandom(null);
            Assert.fail("No exception");
        } catch (Exception e) {
            Assert.assertSame(NullPointerException.class, e.getClass());
            Assert.assertEquals(GenericDaoImpl.REGION_CAN_NOT_BE_NULL, e.getMessage());
        }
    }

    @Test
    public void testEmptyRandom() {
        Optional<Entity<RankedStatsDto>> rankedStatsEntity = rankedStatsDao.getRandom(Region.KR);
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test
    public void testInsertWithNullRankedStatsDto() {
        try {
            rankedStatsDao.save(Region.JP, 10L, null);
            Assert.fail("No exception");
        } catch (Exception e) {
            Assert.assertSame(MongoDaoException.class, e.getClass());
            Assert.assertEquals(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e.getMessage());
        }
    }

    @Test
    public void testInsertWithNullId() {
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        try {
            rankedStatsDao.save(Region.EUW, null, rankedStatsDto);
            Assert.fail("No exception");
        } catch (Exception e) {
            Assert.assertSame(MongoDaoException.class, e.getClass());
            Assert.assertEquals(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e.getMessage());
        }
    }

    @Test
    public void testInsertWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(10);

        // WHEN
        rankedStatsDao.save(Region.EUW, 10L, rankedStatsDto);
        Optional<Entity<RankedStatsDto>> result = rankedStatsDao.get(Region.EUW, 10L);

        // THEN
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem());
    }

    @Test
    public void testRandomWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(11);

        // WHEN
        rankedStatsDao.save(Region.EUW, 11L, rankedStatsDto);
        Optional<Entity<RankedStatsDto>> result = rankedStatsDao.getRandom(Region.EUW);

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
        rankedStatsDto.setSummonerId(12);

        // WHEN
        rankedStatsDao.save(Region.EUW, 12L, rankedStatsDto);
        Optional<Entity<RankedStatsDto>> result = rankedStatsDao.get(Region.EUW, 12L);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem());
    }
}