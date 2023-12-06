package avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaToJsonTest {
    private static final Schema schema = SchemaBuilder.record("TheRecord")
            .fields()
            .name("f1").type().stringType().noDefault()
            .endRecord();

    @Test
    void toMinimizedJson() {
        var expJson = """
                {"type":"record","name":"TheRecord","fields":[{"name":"f1","type":"string"}]}""";
        assertThat(schema.toString()).isEqualTo(expJson);
        assertThat(schema.toString(false)).isEqualTo(expJson);
    }

    @Test
    void toPrettyJson() {
        var expJson = """
                {
                  "type" : "record",
                  "name" : "TheRecord",
                  "fields" : [ {
                    "name" : "f1",
                    "type" : "string"
                  } ]
                }""";
        var actJson = schema.toString(true);
        assertThat(actJson).isEqualTo(expJson);
    }
}