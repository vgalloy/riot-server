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
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.dao.impl.champion.ChampionDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class ChampionDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29501;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final ChampionDao dao = new ChampionDaoImpl(URL + ":" + PORT, "riotTest");

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
    public void testInsertOk() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("Le Blanc");

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.JP, 19L), dto));
        Optional<Entity<ChampionDto, DpoId>> result = dao.get(new DpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(dto, result.get().getItem().get());
    }
}