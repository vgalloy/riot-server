package vgalloy.riot.server.dao.internal.mongo.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.AbstractChampionDaoTest;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.ChampionMongoDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public class ChampionMongoDaoITest extends AbstractChampionDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionMongoDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29501;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    public ChampionMongoDaoITest() {
        super(new ChampionMongoDaoImpl(URL + ":" + PORT, "riotTest"));
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