package fasterxml.xml.databind.serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

/**
 * Serialize a field to JSON by a custom serializer.
 */
class CustomFieldSerializerTest {

    @Test
    void test() throws IOException {
        var artist = new Artist();
        artist.name = "John";

        var mapper = new ObjectMapper();

        var writer = new StringWriter();
        mapper.writeValue(writer, artist);

        assertThatJson(writer.toString()).isEqualTo("{'name': 'JOHN'}");
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
