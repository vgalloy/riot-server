package com.vgalloy.riot.server.webservice.api.dto.impl;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.webservice.api.dto.Dto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChampionNameDto that = (ChampionNameDto) o;
        return Objects.equals(championName, that.championName) &&
            Objects.equals(championId, that.championId) &&
            region == that.region;
    }

    @Override
    public int hashCode() {
        return Objects.hash(championName, championId, region);
    }
}
