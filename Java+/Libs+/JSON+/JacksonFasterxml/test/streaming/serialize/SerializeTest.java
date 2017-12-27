package streaming.serialize;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SerializeTest {

    @Test
    public void array() throws IOException, JSONException {
        OutputStream out = new ByteArrayOutputStream();
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jg = jsonFactory.createGenerator(out, JsonEncoding.UTF8);

        jg.writeStartArray();
        jg.writeStartObject();
        jg.writeStringField("name", "John");
        jg.writeNumberField("age", 30);
        jg.writeEndObject();
        jg.writeEndArray();

        jg.close();

        String actJson = out.toString();
        String expJson = "[{ name: 'John', age: 30}]";

        JSONAssert.assertEquals(expJson, actJson, false);
    }
}
