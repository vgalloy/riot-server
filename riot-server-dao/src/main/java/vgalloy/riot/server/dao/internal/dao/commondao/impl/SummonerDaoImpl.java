package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractCommonDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.entity.dataobject.DataObject;
import vgalloy.riot.server.dao.internal.entity.dataobject.SummonerDo;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class SummonerDaoImpl extends AbstractCommonDao<SummonerDto, SummonerDo> implements SummonerDao {

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

        List<SummonerDto> result = new ArrayList<>();
        DBCursor<SummonerDo> queryResult = collection.find(DBQuery.is("item.name", summonerName).and(DBQuery.is("region", region)));
        result.addAll(queryResult.toArray().stream().map(DataObject::getItem).collect(Collectors.toList()));

        switch (result.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(result.get(0));
            default:
                throw new MongoDaoException("Several summoners for the region : " + region + " and summonerName " + summonerName);
        }
    }
}
