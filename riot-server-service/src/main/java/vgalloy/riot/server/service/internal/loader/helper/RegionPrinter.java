package vgalloy.riot.server.service.internal.loader.helper;

import vgalloy.riot.api.rest.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
public final class RegionPrinter {

    /**
     * Constructor.
     * Prevent instantiation
     */
    private RegionPrinter() {
        throw new AssertionError();
    }

    /**
     * Get the region as string.
     *
     * @param region the region
     * @return the region as a string
     */
    public static String getRegion(Region region) {
        StringBuilder stringBuilder = new StringBuilder("[ ")
                .append(region);
        for (int i = region.toString().length(); i < 5; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.append("]").toString();
    }
}
