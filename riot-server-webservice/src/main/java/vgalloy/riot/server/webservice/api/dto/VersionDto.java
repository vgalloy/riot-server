package vgalloy.riot.server.webservice.api.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 15/04/17.
 *
 * @author Vincent Galloy
 */
public final class VersionDto implements Serializable {

    private static final long serialVersionUID = 6054617140285099203L;
    private final String status;
    private final String version;

    /**
     * Constructor.
     *
     * @param status  the webApp status
     * @param version the version as a string
     */
    public VersionDto(String status, String version) {
        this.status = Objects.requireNonNull(status);
        this.version = Objects.requireNonNull(version);
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }
}
