package avro.guide;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * Serialize and deserialize objects.<br/>
 * <a href="https://avro.apache.org/docs/current/gettingstartedjava.html#Serializing+and+deserializing+without+code+generation">Source</a>
 */
class SerDeTest {

    @Test
    void serDe() throws IOException {
        var schemaIS = SerDeTest.class.getResourceAsStream("user.avsc");
        var schema = new Schema.Parser().parse(schemaIS);

        // Create users
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "Alyssa");
        user1.put("favorite_number", 256);

        GenericRecord user2 = new GenericData.Record(schema);
        user2.put("name", "Ben");
        user2.put("favorite_number", 7);
        user2.put("favorite_color", "red");

        // Serialize user1 and user2 to disk
        var file = File.createTempFile("users", ".avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        var dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.close();

        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        var dataFileReader = new DataFileReader<>(file, datumReader);
        GenericRecord user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }
}