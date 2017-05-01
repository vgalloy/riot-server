package vgalloy.riot.server.webservice.api.dto.impl;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.webservice.api.dto.Dto;

/**
 * Created by Vincent Galloy on 15/04/17.
 *
 * @author Vincent Galloy
 */
public final class AutoCompleteChampionNameDto implements Dto {

    private static final long serialVersionUID = -6821651886728543403L;

    private Region region;
    private String name;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
