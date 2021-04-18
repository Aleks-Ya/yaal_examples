package hello;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.MongoFactory;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloMongoWorldTest {
    @Test
    public void name() throws IOException {
        MongoFactory factory = new MongoFactory();

        MongoDatabase db = factory.getDb();
        db.drop();
        String collectionName = "strings";
        db.createCollection(collectionName);
        MongoCollection<Document> strings = db.getCollection(collectionName);

        Document document = new Document("title", "test");
        strings.insertOne(document);

        assertThat(strings.count(), equalTo(1L));

        factory.stop();
    }
}
