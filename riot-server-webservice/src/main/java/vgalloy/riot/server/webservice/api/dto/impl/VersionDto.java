package vgalloy.riot.server.webservice.api.dto.impl;

import vgalloy.riot.server.webservice.api.dto.Dto;

/**
 * Created by Vincent Galloy on 15/04/17.
 *
 * @author Vincent Galloy
 */
public final class VersionDto implements Dto {

    private static final long serialVersionUID = 6054617140285099203L;

    private String status;
    private String version;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
