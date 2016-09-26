package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractCommonDao;
import vgalloy.riot.server.dao.internal.entity.dataobject.DataObject;
import vgalloy.riot.server.dao.internal.entity.dataobject.MatchDetailDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class MatchDetailDaoImpl extends AbstractCommonDao<MatchDetail, MatchDetailDo> implements MatchDetailDao {

    public static final String COLLECTION_NAME = "matchDetail";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public MatchDetailDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }

    @Override
    public List<MatchDetail> getLastMatchDetail(Region region, long summonerId, int limit) {
        List<MatchDetail> result = new ArrayList<>();
        DBCursor<MatchDetailDo> queryResult = collection.find(DBQuery.is("item.participantIdentities.player.summonerId", summonerId))
                .and(DBQuery.is("region", region))
                .sort(new BasicDBObject("item.matchCreation", -1))
                .limit(limit);
        result.addAll(queryResult.toArray().stream().map(DataObject::getItem).collect(Collectors.toList()));
        return result;
    }
}
