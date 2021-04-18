package avro.record;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GenericRecordToJson {

    @Test
    public void genericRecordToJson() throws IOException {
        String nameField = "name";
        String favoriteNumberField = "favorite_number";
        Schema schema = SchemaBuilder.record("User")
                .fields()
                .name(nameField).type().stringType().noDefault()
                .name(favoriteNumberField).type().intType().noDefault()
                .endRecord();

        GenericRecord genericRecord = new GenericData.Record(schema);
        genericRecord.put(nameField, "John");
        genericRecord.put(favoriteNumberField, 256);

        String actJson = convert(genericRecord);
        String expJson = "{\"name\":\"John\",\"favorite_number\":256}";
        assertThat(actJson, equalTo(expJson));
    }

    private static String convert(GenericRecord genericRecord) throws IOException {
        Schema schema = genericRecord.getSchema();
        GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        OutputStream out = new ByteArrayOutputStream();
        JsonEncoder encoder = EncoderFactory.get().jsonEncoder(schema, out);
        datumWriter.write(genericRecord, encoder);
        encoder.flush();
        return out.toString();
    }
}