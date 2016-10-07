package vgalloy.riot.server.dao.internal.entity.dataobject;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class MatchDetailDo extends DataObject<MatchDetail> {

    private long matchCreationDateFromEpochDay;

    /**
     * Constructor. For Jackson deserialization.
     */
    private MatchDetailDo() {

    }

    /**
     * Constructor.
     *
     * @param region                        the region
     * @param itemId                        the item id
     * @param matchCreationDateFromEpochDay the match creation date from epoch (in days)
     */
    public MatchDetailDo(Region region, Long itemId, long matchCreationDateFromEpochDay) {
        super(region, itemId);
        this.matchCreationDateFromEpochDay = matchCreationDateFromEpochDay;
    }

    public long getMatchCreationDateFromEpochDay() {
        return matchCreationDateFromEpochDay;
    }
}
