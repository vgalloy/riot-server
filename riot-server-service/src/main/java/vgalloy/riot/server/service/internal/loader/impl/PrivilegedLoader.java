package vgalloy.riot.server.service.internal.loader.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.SummonerLoaderClient;
import vgalloy.riot.server.service.api.service.exception.ServiceException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
public class PrivilegedLoader implements Runnable {

    private final SummonerLoaderClient summonerLoaderClient;

    /**
     * Constructor.
     *
     * @param summonerLoaderClient the summonerLoaderClient
     */
    public PrivilegedLoader(SummonerLoaderClient summonerLoaderClient) {
        this.summonerLoaderClient = Objects.requireNonNull(summonerLoaderClient);
    }

    @Override
    public void run() {
        Collection<Long> summonerIdList = new ArrayList<>();
        summonerIdList.add(24550736L); // Ivaranne
        summonerIdList.add(24540988L); // Glenduil
        summonerIdList.add(24523231L); // 3CS Ardemo
        summonerIdList.add(24541689L); // Miir
        summonerIdList.add(24680794L); // LeFielon
        summonerIdList.add(23056233L); // Forlio

        while (true) {
            for (Long currentSummonerId : summonerIdList) {
                summonerLoaderClient.loaderSummoner(Region.EUW, currentSummonerId);
            }
            try {
                Thread.sleep(60 * 60 * 1000); // sleep one hour
            } catch (InterruptedException e) {
                throw new ServiceException(e);
            }
        }
    }
}
