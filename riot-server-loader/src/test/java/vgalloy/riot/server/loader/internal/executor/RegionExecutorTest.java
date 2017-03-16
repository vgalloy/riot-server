package vgalloy.riot.server.loader.internal.executor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.loader.internal.executor.impl.RegionExecutorImpl;
import vgalloy.riot.server.loader.internal.executor.model.Request;

/**
 * Created by Vincent Galloy on 30/10/16.
 *
 * @author Vincent Galloy
 */
public final class RegionExecutorTest {

    @Test
    @Ignore
    public void test() {
        int a = 9;

        int result = 0;
        for (int i = 0; i < 400; i++) {
            if (test(1, a)) {
                result++;
            }
        }
        Assert.assertTrue(result < 60);
        Assert.assertTrue(result > 20);
    }

    private boolean test(int priority1, int priority2) {
        RegionExecutor regionExecutor = new RegionExecutorImpl(Region.EUNE);

        List<Integer> list = new ArrayList<>();

        createAndStartThread(regionExecutor);
        createRequestThread(regionExecutor, list, priority1);
        createRequestThread(regionExecutor, list, priority2);

        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list.get(0) == priority1;
    }

    private void createAndStartThread(RegionExecutor regionExecutor) {
        new Thread(() -> regionExecutor.execute(new Request<>((Query<Integer>) () -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }, 1))).start();
    }

    private void createRequestThread(RegionExecutor regionExecutor, List<Integer> list, int number) {
        new Thread(() -> regionExecutor.execute(new Request<>((Query<Integer>) () -> {
            list.add(number);
            return null;
        }, number))).start();
    }
}