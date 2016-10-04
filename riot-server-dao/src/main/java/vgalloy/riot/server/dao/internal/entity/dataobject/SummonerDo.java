package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

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
     */
    public SummonerDo(Region region, Long itemId) {
        super(region, itemId);
    }
}
