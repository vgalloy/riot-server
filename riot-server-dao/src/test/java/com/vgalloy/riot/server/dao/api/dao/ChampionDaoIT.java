package com.vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.vgalloy.riot.server.dao.DaoTestUtil;
import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import com.vgalloy.riot.server.dao.internal.dao.impl.champion.ChampionDaoImpl;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

/**
 * Created by Vincent Galloy on 01/07/16.
 *
 * @author Vincent Galloy
 */
public final class ChampionDaoIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionDaoIT.class);
    private static final String URL = "localhost";
    private static final int PORT = 29501;

    private static MongodProcess process;
    private static MongodExecutable executable;

    private final ChampionDao dao = new ChampionDaoImpl(URL + ":" + PORT, "riotTest");

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
    public void testInsertOk() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("Le Blanc");

        // WHEN
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.JP, 19L), dto));
        Optional<Entity<ChampionDto, DpoId>> result = dao.get(new CommonDpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(dto, result.get().getItem().get());
    }

    @Test
    public void findChampionByName() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("Lee Sin");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.KR, 19L), dto));
        dto.setName("Lee Sin2");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.KR, 20L), dto));
        dto.setName("Le blanc");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.KR, 21L), dto));

        // WHEN
        List<ChampionName> result = dao.autoCompleteChampionName(Region.KR, "Lee", 10);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Lee Sin", result.get(0).getChampionName());
    }

    @Test
    public void findChampionByName2() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("Olaf");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.LAN, 19L), dto));
        dto.setName("Xin Zhao");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.LAN, 20L), dto));
        dto.setName("Galio");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.LAN, 21L), dto));

        // WHEN
        List<ChampionName> result = dao.autoCompleteChampionName(Region.LAN, "a", 10);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void checkPatternEscape() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("*");
        dao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.OCE, 19L), dto));

        // WHEN
        List<ChampionName> result1 = dao.autoCompleteChampionName(Region.OCE, ".", 10);
        List<ChampionName> result2 = dao.autoCompleteChampionName(Region.OCE, "*", 10);

        // THEN
        Assert.assertNotNull(result1);
        Assert.assertEquals(0, result1.size());
        Assert.assertNotNull(result2);
        Assert.assertEquals(1, result2.size());
    }
}