package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
import vgalloy.riot.api.rest.request.game.dto.GameDto;
import vgalloy.riot.api.rest.request.game.dto.RecentGamesDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class RecentGameDaoImplTest {

    private static final int PORT = 29003;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final RecentGamesDaoImpl recentGamesDao = DaoFactory.getDao(RecentGamesDaoImpl.class, URL + ":" + PORT, "riotTest");

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
    public void testInsertOk() {
        // GIVEN
        RecentGamesDto recentGamesDto = new RecentGamesDto();
        recentGamesDto.setSummonerId(19);
        Set<GameDto> gameDtoSet = new HashSet<>();
        gameDtoSet.add(new GameDto());
        recentGamesDto.setGames(gameDtoSet);

        // WHEN
        recentGamesDao.save(Region.jp, 19L, recentGamesDto);
        Entity<RecentGamesDto> result = recentGamesDao.get(Region.jp, 19L);

        // THEN
        assertNotNull(result);
        assertEquals(recentGamesDto, result.getItem());
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }
}