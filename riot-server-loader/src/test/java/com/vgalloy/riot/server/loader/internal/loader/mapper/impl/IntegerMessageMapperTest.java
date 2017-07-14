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
public final class IntegerMessageMapperTest {

    @Test
    public void testCorrectTransformation() {
        // GIVEN
        LoadingMessageMapper<Integer> mapper = new IntegerMessageMapper(LoaderType.ITEM_BY_ID);
        Integer value = 123;
        LoadingMessage loadingMessage = new LoadingMessage(LoaderType.ITEM_BY_ID, "123");

        // WHEN
        LoadingMessage result = mapper.wrap(value);

        // THEN
        Assert.assertEquals(loadingMessage, result);
    }

    @Test
    public void testDoubleTransformation() {
        // GIVEN
        LoadingMessageMapper<Integer> mapper = new IntegerMessageMapper(LoaderType.ITEM_BY_ID);
        Integer value = 12345;

        // WHEN
        Integer result = mapper.extract(mapper.wrap(value));

        // THEN
        Assert.assertEquals(result, value);
    }
}