package databind.serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Serialize a field to JSON by a custom serializer.
 */
public class CustomFieldSerializerTest {

    @Test
    public void test() throws IOException, JSONException {
        var artist = new Artist();
        artist.name = "John";

        var mapper = new ObjectMapper();

        var writer = new StringWriter();
        mapper.writeValue(writer, artist);

        var exp = "{name: JOHN}";
        JSONAssert.assertEquals(writer.toString(), exp, JSONCompareMode.STRICT);
    }

    private static class Artist {
        @JsonSerialize(using = UpperCaseSerializer.class)
        String name;
    }

    private static class UpperCaseSerializer extends StdSerializer<String> {

        protected UpperCaseSerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.toUpperCase());
        }
    }
}
