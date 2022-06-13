package avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SchemaToJsonTest {
    private static final Schema schema = SchemaBuilder.record("TheRecord")
            .fields()
            .name("f1").type().stringType().noDefault()
            .endRecord();

    @Test
    void toMinimizedJson() {
        var expJson = "{\"type\":\"record\",\"name\":\"TheRecord\",\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";

        var actJson1 = schema.toString();
        assertThat(actJson1, equalTo(expJson));

        var actJson2 = schema.toString(false);
        assertThat(actJson2, equalTo(expJson));
    }

    @Test
    void toPrettyJson() {
        var expJson = "{\n  \"type\" : \"record\",\n  \"name\" : \"TheRecord\",\n  \"fields\" : [ {\n    \"name\" : \"f1\",\n    \"type\" : \"string\"\n  } ]\n}";
        var actJson = schema.toString(true);
        assertThat(actJson, equalTo(expJson));
    }
}