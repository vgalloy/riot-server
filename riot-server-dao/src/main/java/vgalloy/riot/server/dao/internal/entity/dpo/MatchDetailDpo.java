package vgalloy.riot.server.dao.internal.entity.dpo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public class MatchDetailDpo extends AbstractDpo<MatchDetail> {

    private long matchCreationDateFromEpochDay;

    /**
     * Constructor.
     *
     * @param lastUpdate                    the last update
     * @param region                        the region of the item
     * @param itemId                        the item id
     * @param item                          the item
     * @param id                            the id
     * @param matchCreationDateFromEpochDay the match creation date from epoch (in days)
     */
    @JsonCreator
    public MatchDetailDpo(@JsonProperty("lastUpdate") Long lastUpdate,
                          @JsonProperty("region") Region region,
                          @JsonProperty("itemId") Long itemId,
                          @JsonProperty("item") MatchDetail item,
                          @JsonProperty("_id") String id,
                          @JsonProperty("matchCreationDateFromEpochDay") Long matchCreationDateFromEpochDay) {
        super(lastUpdate, region, itemId, item, id);
        this.matchCreationDateFromEpochDay = matchCreationDateFromEpochDay;
    }

    /**
     * Constructor.
     *
     * @param region                        the region
     * @param itemId                        the item id
     * @param matchCreationDateFromEpochDay the match creation date from epoch (in days)
     */
    public MatchDetailDpo(Region region, Long itemId, long matchCreationDateFromEpochDay) {
        super(region, itemId);
        this.matchCreationDateFromEpochDay = matchCreationDateFromEpochDay;
    }

    public long getMatchCreationDateFromEpochDay() {
        return matchCreationDateFromEpochDay;
    }
}
