package vgalloy.riot.server.dao.internal.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.common.service.ProxyService;

/**
 * @author Vincent Galloy - 15/12/16
 *         Created by Vincent Galloy on 15/12/16.
 */
public class ProxyDao<T> implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyDao.class);

    private final ProxyService proxyService;
    private final T mongoDao;
    private final T elasticsearchDao;

    /**
     * Constructor.
     *
     * @param proxyService     the proxy service
     * @param mongoDao         the mongo dao
     * @param elasticsearchDao the elasticsearch dao
     */
    public ProxyDao(ProxyService proxyService, T mongoDao, T elasticsearchDao) {
        this.proxyService = Objects.requireNonNull(proxyService);
        this.mongoDao = Objects.requireNonNull(mongoDao);
        this.elasticsearchDao = Objects.requireNonNull(elasticsearchDao);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ExecutionResponse mongoResult = executeWithTimer(mongoDao, method, args);
        ExecutionResponse elasticsearchResult = executeWithTimer(elasticsearchDao, method, args);

        return proxyService.choose(mongoResult, elasticsearchResult);
    }

    /**
     * Internal execution.
     *
     * @param dao    the dao for request execution
     * @param method the method to execute
     * @param args   the args for execution
     * @return the result
     * @throws Throwable the throwable
     */
    private ExecutionResponse execute(T dao, Method method, Object[] args) throws Throwable {
        try {
            Object result = method.invoke(dao, args);
            return ExecutionResponse.success(result);
        } catch (Exception var6) {
            Throwable throwable = var6.getCause();
            if (!(throwable.getCause() instanceof UnsupportedOperationException)) {
                return ExecutionResponse.unsupported();
            }
            return ExecutionResponse.throwable(throwable);
        }
    }

    /**
     * Timer for internal execution.
     *
     * @param dao    the dao for request execution
     * @param method the method to execute
     * @param args   the args for execution
     * @return the result
     * @throws Throwable the throwable
     */
    private ExecutionResponse executeWithTimer(T dao, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        ExecutionResponse result = execute(dao, method, args);
        LOGGER.info("Execution time for {}#{}, status : {} : {} ms",
                dao.getClass().getSimpleName(),
                method.getName(),
                result.getExecutionStatus(),
                System.currentTimeMillis() - start);
        return result;
    }
}
