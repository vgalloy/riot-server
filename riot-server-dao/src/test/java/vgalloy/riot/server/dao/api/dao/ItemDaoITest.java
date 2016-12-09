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
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.ItemDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class ItemDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29502;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final ItemDao itemDao = new ItemDaoImpl(URL + ":" + PORT, "riotTest");

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
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Trinity force");

        // WHEN
        itemDao.save(new CommonWrapper<>(new ItemId(Region.JP, 19L), itemDto));
        Optional<Entity<ItemDto, ItemId>> result = itemDao.get(new ItemId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(itemDto, result.get().getItem().get());
    }
}