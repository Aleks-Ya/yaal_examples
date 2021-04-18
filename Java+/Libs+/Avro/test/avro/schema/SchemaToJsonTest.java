package avro.schema;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SchemaToJsonTest {
    private static final Schema schema = SchemaBuilder.record("TheRecord")
            .fields()
            .name("f1").type().stringType().noDefault()
            .endRecord();

    @Test
    public void toMinimizedJson() {
        String expJson = "{\"type\":\"record\",\"name\":\"TheRecord\",\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";

        String actJson1 = schema.toString();
        assertThat(actJson1, equalTo(expJson));

        String actJson2 = schema.toString(false);
        assertThat(actJson2, equalTo(expJson));
    }

    @Test
    public void toPrettyJson() {
        String expJson = "{\n  \"type\" : \"record\",\n  \"name\" : \"TheRecord\",\n  \"fields\" : [ {\n    \"name\" : \"f1\",\n    \"type\" : \"string\"\n  } ]\n}";
        String actJson = schema.toString(true);
        assertThat(actJson, equalTo(expJson));
    }
}