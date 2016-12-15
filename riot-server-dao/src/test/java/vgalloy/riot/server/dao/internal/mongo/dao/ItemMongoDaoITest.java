package vgalloy.riot.server.dao.internal.mongo.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.AbstractItemDaoITest;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.ItemMongoDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class ItemMongoDaoITest extends AbstractItemDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemMongoDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29502;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    public ItemMongoDaoITest() {
        super(new ItemMongoDaoImpl(URL + ":" + PORT, "riotTest"));
    }

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
}