package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import org.junit.Ignore;
import org.junit.Test;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.provider.MongoDaoProvider;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public class QueryDaoImplTest {

    @Test
    @Ignore
    public void testWinRate() {
        QueryDao queryDao = MongoDaoProvider.getQueryDao("localhost:28001");
        //queryDao.updateWinRate();
        System.out.println(queryDao.getWinRate(7));
    }

    @Test
    @Ignore
    public void testPosition() {
        QueryDao queryDao = MongoDaoProvider.getQueryDao("localhost:28001");
        //queryDao.updatePosition();
        System.out.println(queryDao.getPosition(24550736, 7));
    }
}
