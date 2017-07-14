package com.vgalloy.riot.server.dao;

import java.io.IOException;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.Slf4jLevel;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.Logger;

/**
 * Created by Vincent Galloy on 09/12/16.
 *
 * @author Vincent Galloy
 */
public final class DaoTestUtil {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private DaoTestUtil() {
        throw new AssertionError();
    }

    /**
     * Create a mongo executable.
     *
     * @param logger the logger
     * @param url    the url of the mongodb
     * @param port   the port
     * @return the executable
     * @throws IOException the io exception
     */
    public static MongodExecutable createMongodExecutable(Logger logger, String url, int port) throws IOException {
        ProcessOutput processOutput = new ProcessOutput(
            Processors.logTo(logger, Slf4jLevel.TRACE),
            Processors.logTo(logger, Slf4jLevel.ERROR), Processors.named("[console>]",
            Processors.logTo(logger, Slf4jLevel.DEBUG)));
        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
            .defaults(Command.MongoD)
            .processOutput(processOutput)
            .build();
        return MongodStarter.getInstance(runtimeConfig)
            .prepare(new MongodConfigBuilder()
                .version(Version.Main.V3_3)
                .net(new Net(url, port, Network.localhostIsIPv6()))
                .build());
    }
}
