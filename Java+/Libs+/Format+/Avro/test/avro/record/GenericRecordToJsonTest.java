package avro.record;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.EncoderFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GenericRecordToJsonTest {

    @Test
    void genericRecordToJson() throws IOException {
        var nameField = "name";
        var favoriteNumberField = "favorite_number";
        var schema = SchemaBuilder.record("User")
                .fields()
                .name(nameField).type().stringType().noDefault()
                .name(favoriteNumberField).type().intType().noDefault()
                .endRecord();

        GenericRecord genericRecord = new GenericData.Record(schema);
        genericRecord.put(nameField, "John");
        genericRecord.put(favoriteNumberField, 256);

        var actJson = convert(genericRecord);
        var expJson = "{\"name\":\"John\",\"favorite_number\":256}";
        assertThat(actJson, equalTo(expJson));
    }

    private static String convert(GenericRecord genericRecord) throws IOException {
        var schema = genericRecord.getSchema();
        var datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        OutputStream out = new ByteArrayOutputStream();
        var encoder = EncoderFactory.get().jsonEncoder(schema, out);
        datumWriter.write(genericRecord, encoder);
        encoder.flush();
        return out.toString();
    }
}