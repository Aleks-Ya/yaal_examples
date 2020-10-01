package avro.no_code_generation;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

/**
 * Serialize and deserialize objects from bytes without code generation.
 */
public class SerDeFromBytesTest {

    @Test
    public void serDe() throws IOException {
        InputStream schemaIS = SerDeFromBytesTest.class.getResourceAsStream("user.avsc");
        Schema schema = new Schema.Parser().parse(schemaIS);

        String nameField = "name";
        String favoriteNumberField = "favorite_number";
        String favoriteColorField = "favorite_color";

        String name1 = "Alyssa";
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put(nameField, name1);
        user1.put(favoriteNumberField, 256);

        String name2 = "Ben";
        GenericRecord user2 = new GenericData.Record(schema);
        user2.put(nameField, name2);
        user2.put(favoriteNumberField, 7);
        user2.put(favoriteColorField, "red");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, os);
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.close();

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        DataFileStream<GenericRecord> dataFileReader = new DataFileStream<>(is, datumReader);
        GenericRecord actRecord = new GenericData.Record(schema);

        assertTrue(dataFileReader.hasNext());
        dataFileReader.next(actRecord);
        assertThat(actRecord.get(nameField).toString(), equalTo(name1));

        assertTrue(dataFileReader.hasNext());
        dataFileReader.next(actRecord);
        assertThat(actRecord.get(nameField).toString(), equalTo(name2));
    }
}