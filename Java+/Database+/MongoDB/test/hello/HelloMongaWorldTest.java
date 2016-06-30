package hello;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloMongaWorldTest {
    @Test
    public void name() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        db.drop();
        String collectionName = "strings";
        db.createCollection(collectionName);
        MongoCollection<Document> strings = db.getCollection(collectionName);

        Document document = new Document("title", "test");

        strings.insertOne(document);

        assertThat(strings.count(), equalTo(1L));
    }
}
