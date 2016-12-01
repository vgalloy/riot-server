package vgalloy.riot.server.loader.internal.loader.mapper;

import vgalloy.riot.server.loader.internal.loader.mapper.impl.IntegerMessageMapper;
import vgalloy.riot.server.loader.internal.loader.mapper.impl.LongMessageMapper;
import vgalloy.riot.server.loader.internal.loader.mapper.impl.StringMessageMapper;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy - 01/12/16
 *         Created by Vincent Galloy on 01/12/16.
 */
public final class LoadingMessageBuilder {

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<Long> summonerId() {
        return new LongMessageMapper(LoadingMessage.LoaderType.SUMMONER_BY_ID);
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<String> summonerName() {
        return new StringMessageMapper(LoadingMessage.LoaderType.SUMMONER_BY_NAME);
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<Integer> itemId() {
        return new IntegerMessageMapper(LoadingMessage.LoaderType.ITEM_BY_ID);
    }
}
