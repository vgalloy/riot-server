package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class SummonerDo extends DataObject<SummonerDto> {

    /**
     * Constructor. For Jackson deserialization.
     */
    private SummonerDo() {

    }

    /**
     * Constructor.
     *
     * @param region the region
     * @param itemId the item id
     * @param item   the item
     */
    public SummonerDo(Region region, Long itemId, SummonerDto item) {
        super(region, itemId, item);
    }
}
