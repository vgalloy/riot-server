package vgalloy.riot.server.dao.internal.mongo.entity;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public interface Identifiable {

    /**
     * The id getter.
     *
     * @return The id
     */
    String getId();

    /**
     * The id setter.
     *
     * @param id The id to set
     */
    void setId(String id);
}
