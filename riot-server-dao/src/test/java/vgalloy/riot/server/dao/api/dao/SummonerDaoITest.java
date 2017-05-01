package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.List;
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
import vgalloy.riot.server.dao.api.entity.GetSummonersQuery;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.dao.impl.summoner.SummonerDaoImpl;

/**
 * Created by Vincent Galloy on 30/06/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29506;

    private static MongodProcess process;
    private static MongodExecutable executable;

    private final SummonerDao summonerDao = new SummonerDaoImpl(URL + ":" + PORT, "riotTest");

    @BeforeClass
    public static void setUp() throws IOException {
        executable = DaoTestUtil.createMongodExecutable(LOGGER, URL, PORT);
        process = executable.start();
    }

    @AfterClass
    public static void tearDown() {
        process.stop();
        executable.stop();
    }

    @Test
    public void testRandomFalse() {
        // GIVEN

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
        summonerDao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.EUW, 2L), summoner));

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
        SummonerDto summoner = new SummonerDto();
        summoner.setName("NAME");
        summoner.setId(2L);
        summonerDao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.EUW, 2L), summoner));

        SummonerDto summoner2 = new SummonerDto();
        summoner2.setName("NAME2");
        summoner2.setId(3L);
        summonerDao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.EUW, 3L), summoner2));

        // THEN
        // all result (max 10)
        List<Entity<SummonerDto, DpoId>> result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.values()));
        Assert.assertEquals(2, result.size());

        // with limit 1
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.values()).setLimit(1));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(summoner, result.get(0).getItem().get());

        // with limit 0
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.values()).setLimit(0));
        Assert.assertEquals(0, result.size());

        // with offset 1
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.values()).setOffset(1));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(summoner2, result.get(0).getItem().get());

        // with 2 names
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.values()).addSummonersName(summoner.getName(), summoner2.getName()));
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(summoner, result.get(0).getItem().get());
        Assert.assertEquals(summoner2, result.get(1).getItem().get());

        // with 1 name and correct region
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.EUW).addSummonersName(summoner2.getName()));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(summoner2, result.get(0).getItem().get());

        // with wrong region
        result = summonerDao.getSummoners(GetSummonersQuery.build().addRegions(Region.BR));
        Assert.assertEquals(0, result.size());
    }
}