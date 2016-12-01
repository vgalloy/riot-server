package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public class RankedStatsDaoITest {

    private static final int PORT = 29503;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RankedStatsDao rankedStatsDao = new RankedStatsDaoImpl(URL + ":" + PORT, "riotTest");

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

    @Test(expected = NullPointerException.class)
    public void testNullRegion() {
        rankedStatsDao.get(new ItemId(null, 1L));
    }

    @Test
    public void testEmptyDatabase() {
        Optional<Entity<CommonWrapper<RankedStatsDto>>> rankedStatsEntity = rankedStatsDao.get(new ItemId(Region.BR, 1L));
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testRandomWithNullRegion() {
        rankedStatsDao.getRandom(null);
    }

    @Test
    public void testEmptyRandom() {
        Optional<Entity<CommonWrapper<RankedStatsDto>>> rankedStatsEntity = rankedStatsDao.getRandom(Region.KR);
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullRankedStatsDto() {
        rankedStatsDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 1L), null));
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullId() {
        rankedStatsDao.save(new CommonWrapper<>(new ItemId(Region.EUW, null), new RankedStatsDto()));
    }

    @Test
    public void testInsertWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(10);

        // WHEN
        rankedStatsDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 10L), rankedStatsDto));
        Optional<Entity<CommonWrapper<RankedStatsDto>>> result = rankedStatsDao.get(new ItemId(Region.EUW, 10L));

        // THEN
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItemWrapper().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItemWrapper().getItem().get());
    }

    @Test
    public void testRandomWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(11);

        // WHEN
        rankedStatsDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 11L), rankedStatsDto));
        Optional<Entity<CommonWrapper<RankedStatsDto>>> result = rankedStatsDao.getRandom(Region.EUW);

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
        rankedStatsDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 12L), rankedStatsDto));
        Optional<Entity<CommonWrapper<RankedStatsDto>>> result = rankedStatsDao.get(new ItemId(Region.EUW, 12L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItemWrapper().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItemWrapper().getItem().get());
    }
}