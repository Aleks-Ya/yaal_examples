package serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
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
 * Custom serialization a Throwable field to JSON.
 */
public class ThrowableSerializerTest {

    @Test
    public void test() throws IOException, JSONException {
        Throwable throwable = new Throwable("my message");
        Data data = new Data();
        data.throwable = throwable;

        ObjectMapper mapper = new ObjectMapper();

        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, data);

//        String exp = "{throwable: {cause: null, localizedMessage: null, message: 'my message', stackTrace: [], suppressed: []}}";
        String exp = "{throwable: {localizedMessage: 'my message', message: 'my message', cause: null}}";

        String actJson = writer.toString();
        System.out.println(actJson);
        JSONAssert.assertEquals(exp, actJson, JSONCompareMode.STRICT);
    }


    private static class Data {
        @JsonSerialize(using = ThrowableSerializer.class)
        Throwable throwable;
    }

    private static class ThrowableSerializer extends StdSerializer<Throwable> {

        protected ThrowableSerializer() {
            super(Throwable.class);
        }

        @Override
        public void serialize(Throwable value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();

            gen.writeStringField("message", value.getMessage());
            gen.writeStringField("localizedMessage", value.getLocalizedMessage());

            serializeCause(value, gen, provider);

            gen.writeEndObject();
        }

        private void serializeCause(Throwable value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            Throwable cause = value.getCause();
            if (cause != null) {
                JsonSerializer<Object> throwableSerializer = provider.findValueSerializer(cause.getClass());
                throwableSerializer.serialize(cause, gen, provider);
            } else {
                gen.writeNullField("cause");
            }
        }
    }
}
