package com.vgalloy.riot.server.loader.internal.loader.mapper.impl;

import org.junit.Assert;
import org.junit.Test;

import com.vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageMapper;
import com.vgalloy.riot.server.loader.internal.loader.message.LoaderType;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class LongMessageMapperTest {

    @Test
    public void testCorrectTransformation() {
        // GIVEN
        LoadingMessageMapper<Long> mapper = new LongMessageMapper(LoaderType.ITEM_BY_ID);
        Long value = 123L;
        LoadingMessage loadingMessage = new LoadingMessage(LoaderType.ITEM_BY_ID, "123");

        // WHEN
        LoadingMessage result = mapper.wrap(value);

        // THEN
        Assert.assertEquals(loadingMessage, result);
    }

    @Test
    public void testDoubleTransformation() {
        // GIVEN
        LoadingMessageMapper<Long> mapper = new LongMessageMapper(LoaderType.ITEM_BY_ID);
        Long value = 12345L;

        // WHEN
        Long result = mapper.extract(mapper.wrap(value));

        // THEN
        Assert.assertEquals(result, value);
    }
}