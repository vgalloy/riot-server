package vgalloy.riot.server.loader.internal.loader.mapper.impl;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageMapper;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public class StringMessageMapperTest {

    @Test
    public void testCorrectTransformation() {
        // GIVEN
        LoadingMessageMapper<String> mapper = new StringMessageMapper(LoadingMessage.LoaderType.ITEM_BY_ID);
        LoadingMessage loadingMessage = new LoadingMessage(LoadingMessage.LoaderType.ITEM_BY_ID, "TESt##12\\");

        // WHEN
        LoadingMessage result = mapper.wrap(loadingMessage.getValue());

        // THEN
        Assert.assertEquals(loadingMessage, result);
    }

    @Test
    public void testDoubleTransformation() {
        // GIVEN
        LoadingMessageMapper<String> mapper = new StringMessageMapper(LoadingMessage.LoaderType.ITEM_BY_ID);
        String value = "TESt##12\\";

        // WHEN
        String result = mapper.extract(mapper.wrap(value));

        // THEN
        Assert.assertEquals(result, value);
    }
}