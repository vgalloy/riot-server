package vgalloy.riot.server.dao.api.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import vgalloy.riot.api.api.dto.game.GameDto;
import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.api.api.dto.lolstaticdata.GoldDto;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.ItemDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RecentGamesDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class ItemDaoITest {

    private static final int PORT = 29501;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final ItemDao itemDao = new ItemDaoImpl(URL + ":" + PORT, "riotTest");

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
    public void testInsertOk() {
        // GIVEN
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Trinity force");

        // WHEN
        itemDao.save(new CommonWrapper<>(new ItemId(Region.JP, 19L), itemDto));
        Optional<Entity<CommonWrapper<ItemDto>>> result = itemDao.get(new ItemId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItemWrapper().getItem().isPresent());
        Assert.assertEquals(itemDto, result.get().getItemWrapper().getItem().get());
    }
}