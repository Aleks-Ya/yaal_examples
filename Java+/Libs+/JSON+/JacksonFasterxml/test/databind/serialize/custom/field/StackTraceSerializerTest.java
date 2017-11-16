package databind.serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Custom serialization a StackTraceElement[] to JSON.
 */
public class StackTraceSerializerTest {
    private final StringWriter writer = new StringWriter();
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(StackTraceElement[].class, new StackTraceSerializer());
        mapper.registerModule(module);
    }

    @Test
    public void stackIsFieldInPojo() throws IOException, JSONException {
        RuntimeException cause = new RuntimeException("cause message");
        cause.setStackTrace(new StackTraceElement[0]);

        StackTraceElement[] stackTrace = {
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        Throwable throwable = new Throwable("my message", cause);
        throwable.setStackTrace(stackTrace);

        mapper.writeValue(writer, throwable);

        String actJson = writer.toString();
        System.out.println(actJson);

        String exp = "{" +
                "message: 'my message', " +
                "localizedMessage: 'my message', " +
                "cause: {message: 'cause message', localizedMessage: 'cause message', cause: null, stackTrace: '', suppressed: []}," +
                "stackTrace: 'my.Class.getName(file:1); my.Class2.getAge(file2:3)'," +
                "suppressed: []}";

        JSONAssert.assertEquals(exp, actJson, JSONCompareMode.STRICT);
    }

    @Test
    public void stackIsObject() throws IOException, JSONException {
        StackTraceElement[] stackTrace = {
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        mapper.writeValue(writer, stackTrace);

        String actJson = writer.toString();
        System.out.println(actJson);

        String exp = "\"my.Class.getName(file:1); my.Class2.getAge(file2:3)\"";

        assertThat(actJson, equalTo(exp));
    }

    @Test
    public void stackIsEmptyArray() throws IOException, JSONException {
        mapper.writeValue(writer, new StackTraceElement[0]);
        assertThat(writer.toString(), equalTo("\"\""));
    }

    @Test
    public void stackIsNull() throws IOException, JSONException {
        mapper.writeValue(writer, null);
        assertThat(writer.toString(), equalTo("null"));
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
