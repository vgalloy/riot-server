package vgalloy.riot.server.dao.internal.mongo.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.AbstractSummonerDaoITest;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.SummonerMongoDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public class SummonerMongoDaoITest extends AbstractSummonerDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerMongoDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29506;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    public SummonerMongoDaoITest() {
        super(new SummonerMongoDaoImpl(URL + ":" + PORT, "riotTest"));
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