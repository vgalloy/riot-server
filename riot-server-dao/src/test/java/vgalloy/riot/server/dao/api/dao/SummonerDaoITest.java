package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
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
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.SummonerDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class SummonerDaoITest {

    private static final int PORT = 29506;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final SummonerDao summonerDao = new SummonerDaoImpl(URL + ":" + PORT, "riotTest");

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
    public void testRandomFalse() {
        // WHEN
        Optional<Entity<SummonerDto, ItemId>> result = summonerDao.getRandom(Region.BR);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testRandomTrue() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setId(2);
        summonerDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 2L), summoner));

        // WHEN
        Optional<Entity<SummonerDto, ItemId>> result = summonerDao.getRandom(Region.EUW);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(summoner, result.get().getItem().get());
    }

    @Test
    public void testSummonerByName() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setName("NAME");
        summoner.setId(2);
        summonerDao.save(new CommonWrapper<>(new ItemId(Region.EUW, 2L), summoner));

        // WHEN
        Optional<SummonerDto> resultEmpty = summonerDao.getSummonerByName(Region.EUW, "azeR");
        Optional<SummonerDto> resultEmpty2 = summonerDao.getSummonerByName(Region.BR, summoner.getName());
        Optional<SummonerDto> result = summonerDao.getSummonerByName(Region.EUW, summoner.getName());

        // THEN
        Assert.assertFalse(resultEmpty.isPresent());
        Assert.assertFalse(resultEmpty2.isPresent());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(summoner, result.get());
    }
}