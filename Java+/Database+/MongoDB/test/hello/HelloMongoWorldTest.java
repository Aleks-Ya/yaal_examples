package hello;

import common.MongoFactory;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HelloMongoWorldTest {
    @Test
    void name() throws IOException {
        var factory = new MongoFactory();

        var db = factory.getDb();
        db.drop();
        var collectionName = "strings";
        db.createCollection(collectionName);
        var strings = db.getCollection(collectionName);

        var document = new Document("title", "test");
        strings.insertOne(document);

        assertThat(strings.countDocuments()).isEqualTo(1L);

        factory.stop();
    }
}
