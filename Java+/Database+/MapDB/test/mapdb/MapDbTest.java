package mapdb;

import org.junit.jupiter.api.Test;
import org.mapdb.DBMaker;
import util.FileUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapdb.Serializer.STRING;

class MapDbTest {
    @Test
    void memoryDB() {
        try (var db = DBMaker.memoryDB().make();
             var map = db.hashMap("map")
                     .keySerializer(STRING)
                     .valueSerializer(STRING)
                     .createOrOpen()) {
            var expValue = "here";
            var key = "something";
            map.put(key, expValue);
            var actValue = map.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }

    @Test
    void tempFileDB() {
        try (var db = DBMaker.tempFileDB().make();
             var map = db.hashMap("map")
                     .keySerializer(STRING)
                     .valueSerializer(STRING)
                     .createOrOpen()) {
            var expValue = "here";
            var key = "something";
            map.put(key, expValue);
            var actValue = map.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }

    @Test
    void fileDB() {
        var file = FileUtil.createAbsentTempFile(".db");
        System.out.println("DB file: " + file.getAbsolutePath());
        var expValue = "value1";
        var key = "key1";
        try (var db = DBMaker.fileDB(file).make();
             var map = db.hashMap("map")
                     .keySerializer(STRING)
                     .valueSerializer(STRING)
                     .createOrOpen()) {
            map.put(key, expValue);
        }
        try (var db = DBMaker.fileDB(file).make();
             var map = db.hashMap("map")
                     .keySerializer(STRING)
                     .valueSerializer(STRING)
                     .createOrOpen()) {
            assertThat(map.get(key)).isEqualTo(expValue);
        }
    }
}