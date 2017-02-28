package vgalloy.riot.server.dao.internal.dao.impl.summoner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class GetSummonersQuery {

    private final List<String> summonersName;
    private final List<Region> regions;
    private Integer limit;
    private Integer offset;

    /**
     * Constructor.
     * To prevent external instantiation
     */
    private GetSummonersQuery() {
        summonersName = new ArrayList<>();
        regions = new ArrayList<>();
        limit = 10;
        offset = 0;
    }

    /**
     * Create a default query.
     *
     * @return the query with default parameter
     */
    public static GetSummonersQuery build() {
        return new GetSummonersQuery();
    }

    /**
     * Add summoners name.
     *
     * @param summonersName the summoners name
     * @return this
     */
    public GetSummonersQuery addSummonersName(Collection<String> summonersName) {
        Objects.requireNonNull(summonersName);
        this.summonersName.addAll(summonersName);
        return this;
    }

    /**
     * Add summoners name.
     *
     * @param summonersName the summoners name
     * @return this
     */
    public GetSummonersQuery addSummonersName(String... summonersName) {
        Objects.requireNonNull(summonersName);
        this.summonersName.addAll(Arrays.asList(summonersName));
        return this;
    }

    /**
     * Add regions.
     *
     * @param regions the regions
     * @return this
     */
    public GetSummonersQuery addRegions(Collection<Region> regions) {
        Objects.requireNonNull(regions);
        this.regions.addAll(regions);
        return this;
    }

    /**
     * Add regions.
     *
     * @param regions the regions
     * @return this
     */
    public GetSummonersQuery addRegions(Region... regions) {
        Objects.requireNonNull(regions);
        this.regions.addAll(Arrays.asList(regions));
        return this;
    }

    public List<String> getSummonersName() {
        return summonersName;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public Integer getLimit() {
        return limit;
    }

    /**
     * Set the limit for the number of values.
     *
     * @param limit the limit
     * @return this
     */
    public GetSummonersQuery setLimit(Integer limit) {
        Objects.requireNonNull(limit);
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    /**
     * Set the offset for the values.
     *
     * @param offset the offset
     * @return this
     */
    public GetSummonersQuery setOffset(Integer offset) {
        Objects.requireNonNull(offset);
        this.offset = offset;
        return this;
    }
}
