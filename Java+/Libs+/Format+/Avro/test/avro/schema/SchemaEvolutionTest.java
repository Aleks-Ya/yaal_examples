package avro.schema;

import org.apache.avro.AvroTypeException;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchemaEvolutionTest {
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";
    private static final String EMPLOYED_FIELD = "employed";
    private static final String GENDER_FIELD = "gender";

    private static final String NAME_VALUE = "John";
    private static final int AGE_VALUE = 30;
    private static final boolean EMPLOYED_VALUE = true;
    private static final boolean EMPLOYED_VALUE_DEFAULT = false;

    private static final Schema schema1 = SchemaBuilder.record("Person").namespace("data")
            .fields()
            .name(NAME_FIELD).type().stringType().noDefault()
            .name(AGE_FIELD).type().optional().intType()
            .endRecord();
    private static final Schema schema2 = SchemaBuilder.record("Person").namespace("data")
            .fields()
            .name(NAME_FIELD).type().stringType().noDefault()
            .name(AGE_FIELD).type().optional().intType()
            .name(EMPLOYED_FIELD).type().booleanType().booleanDefault(EMPLOYED_VALUE_DEFAULT)
            .endRecord();
    private static final Schema schema3 = SchemaBuilder.record("Person").namespace("data")
            .fields()
            .name(NAME_FIELD).type().stringType().noDefault()
            .name(AGE_FIELD).type().optional().intType()
            .name(EMPLOYED_FIELD).type().booleanType().booleanDefault(EMPLOYED_VALUE_DEFAULT)
            .name(GENDER_FIELD).type().stringType().noDefault()
            .endRecord();

    @Test
    void backwardCompatibility() {
        var person1 = new GenericData.Record(schema1);
        person1.put(NAME_FIELD, NAME_VALUE);
        person1.put(AGE_FIELD, AGE_VALUE);

        var person1Bytes = writeRecordToBytes(person1);

        var person2Map = bytesToMap(person1Bytes, schema2);
        var expPerson2Map = Map.of(
                NAME_FIELD, NAME_VALUE,
                AGE_FIELD, AGE_VALUE,
                EMPLOYED_FIELD, EMPLOYED_VALUE_DEFAULT
        );
        assertThat(person2Map, equalTo(expPerson2Map));
    }

    @Test
    void backwardCompatibilityBroken() {
        var person1 = new GenericData.Record(schema1);
        person1.put(NAME_FIELD, NAME_VALUE);
        person1.put(AGE_FIELD, AGE_VALUE);

        var person1Bytes = writeRecordToBytes(person1);

        var e = assertThrows(AvroTypeException.class, () -> bytesToMap(person1Bytes, schema3));
        assertThat(e.getMessage(), equalTo("Found data.Person, expecting data.Person, missing required field gender"));
    }

    @Test
    void forwardCompatibility() {
        var person2 = new GenericData.Record(schema2);
        person2.put(NAME_FIELD, NAME_VALUE);
        person2.put(AGE_FIELD, AGE_VALUE);
        person2.put(EMPLOYED_FIELD, EMPLOYED_VALUE);

        var person2Bytes = writeRecordToBytes(person2);

        var person1Map = bytesToMap(person2Bytes, schema1);
        var expPerson1Map = Map.of(
                NAME_FIELD, NAME_VALUE,
                AGE_FIELD, AGE_VALUE
        );
        assertThat(person1Map, equalTo(expPerson1Map));
    }

    private HashMap<Object, Object> bytesToMap(byte[] bytes, Schema schema) {
        try {
            var is = new ByteArrayInputStream(bytes);
            var datumReader = new GenericDatumReader<GenericRecord>(schema);
            var map = new HashMap<>();
            try (var stream = new DataFileStream<>(is, datumReader)) {
                assertTrue(stream.hasNext());
                var actRecord = new GenericData.Record(schema);
                stream.next(actRecord);
                for (var field : actRecord.getSchema().getFields()) {
                    var name = field.name();
                    var value = actRecord.get(name);
                    if (value instanceof Utf8) {
                        value = value.toString();
                    }
                    map.put(name, value);
                }
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] writeRecordToBytes(GenericData.Record record) {
        try {
            var schema = record.getSchema();
            var os = new ByteArrayOutputStream();
            var datumWriter = new GenericDatumWriter<GenericRecord>(schema);
            try (var dataFileWriter = new DataFileWriter<>(datumWriter)) {
                dataFileWriter.create(schema, os);
                dataFileWriter.append(record);
            }
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}