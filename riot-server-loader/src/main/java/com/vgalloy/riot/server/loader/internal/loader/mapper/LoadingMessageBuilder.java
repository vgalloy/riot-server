package com.vgalloy.riot.server.loader.internal.loader.mapper;

import com.vgalloy.riot.server.loader.internal.loader.mapper.impl.IntegerMessageMapper;
import com.vgalloy.riot.server.loader.internal.loader.mapper.impl.LongMessageMapper;
import com.vgalloy.riot.server.loader.internal.loader.mapper.impl.StringMessageMapper;
import com.vgalloy.riot.server.loader.internal.loader.message.LoaderType;

/**
 * Created by Vincent Galloy on 01/12/16.
 * Allow MessageMapper creation for each kind of {@link LoaderType}.
 *
 * @author Vincent Galloy
 */
public final class LoadingMessageBuilder {

    private static final LoadingMessageMapper<Long> SUMMONER_ID_MAPPER = new LongMessageMapper(LoaderType.SUMMONER_BY_ID);
    private static final LoadingMessageMapper<String> SUMMONER_NAME_MAPPER = new StringMessageMapper(LoaderType.SUMMONER_BY_NAME);
    private static final LoadingMessageMapper<Integer> ITEM_ID_MAPPER = new IntegerMessageMapper(LoaderType.ITEM_BY_ID);
    private static final LoadingMessageMapper<Long> CHAMPION_ID_MAPPER = new LongMessageMapper(LoaderType.CHAMPION_BY_ID);

    /**
     * Constructor.
     * To prevent instantiation
     */
    private LoadingMessageBuilder() {
        throw new AssertionError();
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<Long> summonerId() {
        return SUMMONER_ID_MAPPER;
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<String> summonerName() {
        return SUMMONER_NAME_MAPPER;
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<Integer> itemId() {
        return ITEM_ID_MAPPER;
    }

    /**
     * Obtains a mapper.
     *
     * @return the corresponding mapper
     */
    public static LoadingMessageMapper<Long> championId() {
        return CHAMPION_ID_MAPPER;
    }
}
