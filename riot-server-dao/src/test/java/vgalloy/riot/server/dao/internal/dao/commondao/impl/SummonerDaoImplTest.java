package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.io.IOException;

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
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class SummonerDaoImplTest {

    private static final int PORT = 29004;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final SummonerDaoImpl summonerDao = DaoFactory.getDao(SummonerDaoImpl.class, URL + ":" + PORT, "riotTest");

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
    public void testRandomFalse() {
        // WHEN
        Entity<SummonerDto> result = summonerDao.getRandom(Region.br);

        // THEN
        assertNull(result);
    }

    @Test
    public void testRandomTrue() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setId(2);
        summonerDao.save(Region.euw, 2L, summoner);

        // WHEN
        Entity<SummonerDto> result = summonerDao.getRandom(Region.euw);

        // THEN
        assertNotNull(result);
        assertEquals(summoner, result.getItem());
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }
}