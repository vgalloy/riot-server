package vgalloy.riot.server.dao.internal.dao.impl.summoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import org.mongojack.DBQuery;

import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.entity.dpo.SummonerDpo;
import vgalloy.riot.server.dao.internal.entity.mapper.DpoMapper;

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
    public List<Entity<SummonerDto, DpoId>> getSummoners(GetSummonersQuery getSummonersQuery) {
        Objects.requireNonNull(getSummonersQuery);

        // Unknown behavior with limit 0
        if (getSummonersQuery.getLimit() == 0) {
            return new ArrayList<>();
        }

        DBQuery.Query dbQuery = DBQuery.empty();
        if (!getSummonersQuery.getRegions().isEmpty()) {
            dbQuery.in("region", getSummonersQuery.getRegions());
        }
        if (!getSummonersQuery.getSummonersName().isEmpty()) {
            dbQuery.in("item.name", getSummonersQuery.getSummonersName());
        }

        return collection.find(dbQuery)
                .sort(new BasicDBObject("_id", 1))
                .skip(getSummonersQuery.getOffset())
                .limit(getSummonersQuery.getLimit())
                .toArray()
                .stream()
                .map(DpoMapper::mapToEntity)
                .collect(Collectors.toList());
    }
}
