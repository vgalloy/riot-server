package vgalloy.riot.server.dao.internal.dao.impl;

import java.util.Objects;
import java.util.Optional;

import org.mongojack.DBQuery;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.entity.dpo.SummonerDpo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class SummonerDaoImpl extends AbstractDao<SummonerDto, SummonerDpo> implements SummonerDao {

    public static final String COLLECTION_NAME = "summoner";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public SummonerDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }

    @Override
    public Optional<SummonerDto> getSummonerByName(Region region, String summonerName) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerName);

        Optional<SummonerDpo> summonerDo = Optional.ofNullable(collection.findOne(DBQuery.is("item.name", summonerName).and(DBQuery.is("region", region))));

        if (summonerDo.isPresent()) {
            return Optional.of(summonerDo.get().getItem());
        }
        return Optional.empty();
    }
}
