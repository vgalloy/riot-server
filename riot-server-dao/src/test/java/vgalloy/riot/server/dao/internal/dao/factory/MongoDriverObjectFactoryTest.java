package vgalloy.riot.server.dao.internal.dao.factory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vincent Galloy - 06/10/16
 *         Created by Vincent Galloy on 06/10/16.
 */
public class MongoDriverObjectFactoryTest {

    private static final int PORT = 29601;
    private static final String URL = "localhost";

    @Test
    public void testMongoClientCreation() {
        MongoClient result1 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).get();
        MongoClient result2 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).get();

        Assert.assertEquals(result1, result2);
    }

    @Test
    public void testMongoDatabaseCreation() {
        MongoDatabase result1 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest").get();
        MongoDatabase result2 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest2").get();
        MongoDatabase result3 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest").get();

        Assert.assertNotEquals(result1, result2);
        Assert.assertEquals(result1, result3);
    }

    @Test
    public void testMongoCollectionCreation() {
        MongoCollection<?> result1 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest").getMongoCollection("collection").get();
        MongoCollection<?> result2 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest2").getMongoCollection("collection2").get();
        MongoCollection<?> result3 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest").getMongoCollection("collection").get();
        MongoCollection<?> result4 = MongoDriverObjectFactory.getMongoClient(URL + ":" + PORT).getMongoDatabase("riotTest2").getMongoCollection("collection").get();

        Assert.assertNotEquals(result1, result2);
        Assert.assertNotEquals(result1, result4);
        Assert.assertNotEquals(result2, result4);
        Assert.assertEquals(result1, result3);
    }
}