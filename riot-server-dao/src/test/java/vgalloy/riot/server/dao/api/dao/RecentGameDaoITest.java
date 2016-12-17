package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.game.GameDto;
import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RecentGamesDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class RecentGameDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29505;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RecentGamesDao recentGamesDao = new RecentGamesDaoImpl(URL + ":" + PORT, "riotTest");

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
        RecentGamesDto recentGamesDto = new RecentGamesDto();
        recentGamesDto.setSummonerId(19L);
        Set<GameDto> gameDtoSet = new HashSet<>();
        gameDtoSet.add(new GameDto());
        recentGamesDto.setGames(gameDtoSet);

        // WHEN
        recentGamesDao.save(new CommonDpoWrapper<>(new DpoId(Region.JP, 19L), recentGamesDto));
        Optional<Entity<RecentGamesDto, DpoId>> result = recentGamesDao.get(new DpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(recentGamesDto, result.get().getItem().get());
    }
}