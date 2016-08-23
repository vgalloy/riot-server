package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.io.IOException;

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
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public class MatchDetailDaoImplTest {

    private static final int PORT = 29001;
    private static final String URL = "localhost";

    private static MongodProcess PROCESS;
    private static MongodExecutable EXECUTABLE;

    private final MatchDetailDaoImpl matchDetailService = DaoFactory.getDao(MatchDetailDaoImpl.class, URL + ":" + PORT, "riotTest");

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
    public void testInsertWithCorrectMatchDetailDto() {
        // GIVEN
        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setMatchId(10);

        // WHEN
        matchDetailService.save(Region.euw, 10L, matchDetail);
        Entity<MatchDetail> result = matchDetailService.get(Region.euw, 10L);

        // THEN
        assertNotNull(result);
        assertEquals(matchDetail, result.getItem());
    }

    @AfterClass
    public static void tearDown() {
        PROCESS.stop();
        EXECUTABLE.stop();
    }
}