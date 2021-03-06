package com.vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.vgalloy.riot.server.dao.DaoTestUtil;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import com.vgalloy.riot.server.dao.internal.dao.impl.RecentGamesDaoImpl;
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

/**
 * Created by Vincent Galloy on 01/07/16.
 *
 * @author Vincent Galloy
 */
public final class RecentGameDaoIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsDaoIT.class);
    private static final String URL = "localhost";
    private static final int PORT = 29505;

    private static MongodProcess process;
    private static MongodExecutable executable;

    private final RecentGamesDao recentGamesDao = new RecentGamesDaoImpl(URL + ":" + PORT, "riotTest");

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
        RecentGamesDto recentGamesDto = new RecentGamesDto();
        recentGamesDto.setSummonerId(19L);
        Set<GameDto> gameDtoSet = new HashSet<>();
        gameDtoSet.add(new GameDto());
        recentGamesDto.setGames(gameDtoSet);

        // WHEN
        recentGamesDao.save(new CommonDpoWrapper<>(new CommonDpoId(Region.JP, 19L), recentGamesDto));
        Optional<Entity<RecentGamesDto, DpoId>> result = recentGamesDao.get(new CommonDpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(recentGamesDto, result.get().getItem().get());
    }
}