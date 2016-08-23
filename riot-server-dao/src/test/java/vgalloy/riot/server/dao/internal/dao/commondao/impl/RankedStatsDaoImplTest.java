package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.io.IOException;
import java.util.ArrayList;

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

import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.stats.dto.ChampionStatsDto;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;
import vgalloy.riot.server.dao.internal.entity.Key;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public class RankedStatsDaoImplTest {

    private static final int PORT = 29002;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RankedStatsDaoImpl rankedStatsDao = DaoFactory.getDao(RankedStatsDaoImpl.class, URL + ":" + PORT, "riotTest");

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
    public void testNullRegion() {
        try {
            rankedStatsDao.get(null, 1L);
            fail("No exception");
        } catch (Exception e) {
            assertSame(NullPointerException.class, e.getClass());
            assertEquals(Key.REGION_CAN_NOT_BE_NULL, e.getMessage());
        }
    }

    @Test
    public void testEmptyDatabase() {
        Entity<RankedStatsDto> rankedStatsEntity = rankedStatsDao.get(Region.br, 1L);
        assertNull(rankedStatsEntity);
    }

    @Test
    public void testRandomWithNullRegion() {
        try {
            rankedStatsDao.getRandom(null);
            fail("No exception");
        } catch (Exception e) {
            assertSame(NullPointerException.class, e.getClass());
            assertEquals(GenericDaoImpl.REGION_CAN_NOT_BE_NULL, e.getMessage());
        }
    }

    @Test
    public void testEmptyRandom() {
        Entity<RankedStatsDto> rankedStatsEntity = rankedStatsDao.getRandom(Region.kr);
        assertNull(rankedStatsEntity);
    }

    @Test
    public void testInsertWithNullRankedStatsDto() {
        try {
            rankedStatsDao.save(Region.jp, 10L, null);
            fail("No exception");
        } catch (Exception e) {
            assertSame(MongoDaoException.class, e.getClass());
            assertEquals(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e.getMessage());
        }
    }

    @Test
    public void testInsertWithNullId() {
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        try {
            rankedStatsDao.save(Region.euw, null, rankedStatsDto);
            fail("No exception");
        } catch (Exception e) {
            assertSame(MongoDaoException.class, e.getClass());
            assertEquals(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e.getMessage());
        }
    }

    @Test
    public void testInsertWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(10);

        // WHEN
        rankedStatsDao.save(Region.euw, 10L, rankedStatsDto);
        Entity<RankedStatsDto> result = rankedStatsDao.get(Region.euw, 10L);

        // THEN
        assertNotNull(result);
        assertEquals(rankedStatsDto, result.getItem());
    }

    @Test
    public void testRandomWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(11);

        // WHEN
        rankedStatsDao.save(Region.euw, 11L, rankedStatsDto);
        Entity<RankedStatsDto> result = rankedStatsDao.getRandom(Region.euw);

        // THEN
        assertNotNull(result);
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
        rankedStatsDao.save(Region.euw, 12L, rankedStatsDto);
        Entity<RankedStatsDto> result = rankedStatsDao.get(Region.euw, 12L);

        // THEN
        assertNotNull(result);
        System.out.printf(result.toString());
        assertEquals(rankedStatsDto, result.getItem());
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }
}