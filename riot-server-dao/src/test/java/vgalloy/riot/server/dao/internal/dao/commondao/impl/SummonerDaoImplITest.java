package vgalloy.riot.server.dao.internal.dao.commondao.impl;

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
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class SummonerDaoImplITest {

    private static final int PORT = 29004;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final SummonerDaoImpl summonerDao = DaoFactory.getDao(SummonerDaoImpl.class, URL + ":" + PORT, "riotTest");

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
        Optional<Entity<SummonerDto>> result = summonerDao.getRandom(Region.BR);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testRandomTrue() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setId(2);
        summonerDao.save(Region.EUW, 2L, summoner);

        // WHEN
        Optional<Entity<SummonerDto>> result = summonerDao.getRandom(Region.EUW);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(summoner, result.get().getItem());
    }

    @Test
    public void testSummonerByName() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setName("NAME");
        summoner.setId(2);
        summonerDao.save(Region.EUW, 2L, summoner);

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