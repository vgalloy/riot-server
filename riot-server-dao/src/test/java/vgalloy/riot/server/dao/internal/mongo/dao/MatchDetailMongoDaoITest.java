package vgalloy.riot.server.dao.internal.mongo.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.DaoTestUtil;
import vgalloy.riot.server.dao.api.dao.AbstractMatchDetailDaoITest;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.GlobalMatchDetailMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.MatchDetailMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.TimelineMongoDaoImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public class MatchDetailMongoDaoITest extends AbstractMatchDetailDaoITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailMongoDaoITest.class);
    private static final String URL = "localhost";
    private static final int PORT = 29503;

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    public MatchDetailMongoDaoITest() {
        super(new GlobalMatchDetailMongoDaoImpl(new TimelineMongoDaoImpl(URL + ":" + PORT, "riotTest"), new MatchDetailMongoDaoImpl(URL + ":" + PORT, "riotTest")));
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