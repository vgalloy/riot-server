package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
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
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.dao.impl.SummonerDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class SummonerDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29506;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final SummonerDao summonerDao = new SummonerDaoImpl(URL + ":" + PORT, "riotTest");

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
    public void testRandomFalse() {
        // WHEN
        Optional<Entity<SummonerDto, DpoId>> result = summonerDao.getRandom(Region.BR);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testRandomTrue() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setId(2L);
        summonerDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 2L), summoner));

        // WHEN
        Optional<Entity<SummonerDto, DpoId>> result = summonerDao.getRandom(Region.EUW);

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
        summoner.setId(2L);
        summonerDao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 2L), summoner));

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