package vgalloy.riot.server.dao.internal.mongo.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.AbstractRankedStatsDaoITest;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.RankedStatsMongoDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public class RankedStatsMongoDaoITest extends AbstractRankedStatsDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsMongoDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29504;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    public RankedStatsMongoDaoITest() {
        super(new RankedStatsMongoDaoImpl(URL + ":" + PORT, "riotTest"));
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