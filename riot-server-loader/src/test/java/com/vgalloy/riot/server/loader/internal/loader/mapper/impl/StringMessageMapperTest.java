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
public final class StringMessageMapperTest {

    @Test
    public void testCorrectTransformation() {
        // GIVEN
        LoadingMessageMapper<String> mapper = new StringMessageMapper(LoaderType.ITEM_BY_ID);
        LoadingMessage loadingMessage = new LoadingMessage(LoaderType.ITEM_BY_ID, "TESt##12\\");

        // WHEN
        LoadingMessage result = mapper.wrap(loadingMessage.getValue());

        // THEN
        Assert.assertEquals(loadingMessage, result);
    }

    @Test
    public void testDoubleTransformation() {
        // GIVEN
        LoadingMessageMapper<String> mapper = new StringMessageMapper(LoaderType.ITEM_BY_ID);
        String value = "TESt##12\\";

        // WHEN
        String result = mapper.extract(mapper.wrap(value));

        // THEN
        Assert.assertEquals(result, value);
    }
}