package streaming.serialize;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

public class SerializeTest {

    @Test
    public void array() throws IOException {
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
        var expJson = "[{ name: 'John', age: 30}]";

        assertJsonEquals(expJson, actJson);
    }
}
