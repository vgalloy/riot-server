package vgalloy.riot.server.dao.internal.mapper;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.ChampionName;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.dao.internal.entity.dpo.ChampionDpo;

/**
 * Created by Vincent Galloy on 15/04/17.
 *
 * @author Vincent Galloy
 */
public final class ChampionNameMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ChampionNameMapper() {
        throw new AssertionError();
    }

    /**
     * Map {@link ChampionDpo} into {@link ChampionName}.
     *
     * @param championDpo the champion dto
     * @return the champion name
     */
    public static ChampionName map(ChampionDpo championDpo) {
        Objects.requireNonNull(championDpo);

        CommonDpoId commonDpoId = new CommonDpoId(championDpo.getRegion(), championDpo.getItemId());
        return new ChampionName(commonDpoId, championDpo.getItem().getName());
    }
}
