package vgalloy.riot.server.service.internal.task;

import java.util.Objects;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * @author Vincent Galloy - 06/12/16
 *         Created by Vincent Galloy on 06/12/16.
 */
public class PrivilegedLoaderTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegedLoaderTask.class);

    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param loaderClient the loader client
     */
    public PrivilegedLoaderTask(LoaderClient loaderClient) {
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public void run() {
        try {
            loaderClient.loadAsyncSummonerById(Region.EUW, 24550736L); // Ivaranne
            loaderClient.loadAsyncSummonerById(Region.EUW, 24540988L); // Glenduil
            loaderClient.loadAsyncSummonerById(Region.EUW, 24523231L); // 3CS Ardemo
            loaderClient.loadAsyncSummonerById(Region.EUW, 24541689L); // Miir
            loaderClient.loadAsyncSummonerById(Region.EUW, 24680794L); // LeFielon
            loaderClient.loadAsyncSummonerById(Region.EUW, 23056233L); // Forlio
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
