package vgalloy.riot.server.service.internal.executor;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.query.Query;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/07/16.
 */
public interface Executor {

    /**
     * Add the request into the execution list.
     *
     * @param request  the request
     * @param region   the region
     * @param priority the priority
     * @param <DTO>    the dto type
     * @return the dto
     */
    // TODO Can executor implements riotApi interface ?
    <DTO> DTO execute(Query<DTO> request, Region region, int priority);
}
