package vgalloy.riot.server.webservice.internal.mapper.summonerid;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.service.exception.ServiceException;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerIdMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private SummonerIdMapper() {
        throw new AssertionError();
    }

    /**
     * Serialize summonerId into String.
     *
     * @param summonerId the summoner id as an object
     * @return the summoner id as a String
     */
    public static String map(SummonerId summonerId) {
        return summonerId.getRegion().name() + summonerId.getId();
    }

    /**
     * Deserialize summonerId from String.
     *
     * @param string the summonerId as a String
     * @return the summonerId as an object
     */
    public static SummonerId map(String string) {
        Objects.requireNonNull(string);
        Pattern pattern = Pattern.compile("([A-Z]*)([0-9]*)");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()) {
            throw new ServiceException("Can not convert " + string + " into SummonerId");
        }
        Region region = Region.valueOf(matcher.group(1));
        Long id = Long.valueOf(matcher.group(2));
        return new SummonerId(region, id);
    }
}
