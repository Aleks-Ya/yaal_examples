package fasterxml.xml.streaming.serialize;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class SerializeTest {

    @Test
    void array() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        var jsonFactory = new JsonFactory();
        var jg = jsonFactory.createGenerator(out, JsonEncoding.UTF8);

        jg.writeStartArray();
        jg.writeStartObject();
        jg.writeStringField("name", "John");
        jg.writeNumberField("age", 30);
        jg.writeEndObject();
        jg.writeEndArray();

        jg.close();

        var actJson = out.toString();

        assertThatJson(actJson).isEqualTo("[{ name: 'John', age: 30}]");
    }
}
