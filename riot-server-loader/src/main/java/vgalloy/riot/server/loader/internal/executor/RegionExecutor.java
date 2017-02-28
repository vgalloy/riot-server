package vgalloy.riot.server.loader.internal.executor;

import vgalloy.riot.server.loader.internal.executor.model.Request;

/**
 * Created by Vincent Galloy on  30/06/16.
 *
 * @author Vincent Galloy
 */
public interface RegionExecutor {

    /**
     * Add the request into the execution list.
     *
     * @param request the request
     * @param <DTO>   the dto type
     * @return the dto
     */
    <DTO> DTO execute(Request<DTO> request);
}
