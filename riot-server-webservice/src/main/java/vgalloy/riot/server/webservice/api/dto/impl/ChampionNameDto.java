package vgalloy.riot.server.webservice.api.dto.impl;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.webservice.api.dto.Dto;

/**
 * Created by Vincent Galloy on 01/05/17.
 *
 * @author Vincent Galloy
 */
public final class ChampionNameDto implements Dto {

    private static final long serialVersionUID = -4033322895030287511L;

    private String championName;
    private Long championId;
    private Region region;

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
