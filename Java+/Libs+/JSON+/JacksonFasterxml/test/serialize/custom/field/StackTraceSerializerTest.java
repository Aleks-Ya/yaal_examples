package serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom serialization a StackTraceElement[] to JSON.
 */
public class StackTraceSerializerTest {

    @Test
    public void test() throws IOException, JSONException {
        RuntimeException cause = new RuntimeException("cause message");
        cause.setStackTrace(new StackTraceElement[0]);

        StackTraceElement[] stackTrace = {
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        Throwable throwable = new Throwable("my message", cause);
        throwable.setStackTrace(stackTrace);

        SimpleModule module = new SimpleModule();
        module.addSerializer(StackTraceElement[].class, new StackTraceSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, throwable);

        String exp = "{" +
                "message: 'my message', " +
                "localizedMessage: 'my message', " +
                "cause: {message: 'cause message', localizedMessage: 'cause message', cause: null, stackTrace: '', suppressed: []}," +
                "stackTrace: 'my.Class.getName(file:1); my.Class2.getAge(file2:3)'," +
                "suppressed: []}";

        String actJson = writer.toString();
        System.out.println(actJson);
        JSONAssert.assertEquals(exp, actJson, JSONCompareMode.STRICT);
    }

    private static class StackTraceSerializer extends StdScalarSerializer<StackTraceElement[]> {

        StackTraceSerializer() {
            super(StackTraceElement[].class);
        }

        @Override
        public void serialize(StackTraceElement[] stack, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (stack != null) {
                String valueStr = Stream.of(stack).map(StackTraceElement::toString).collect(Collectors.joining("; "));
                gen.writeString(valueStr);
            } else {
                gen.writeNull();
            }
        }

    }
}
