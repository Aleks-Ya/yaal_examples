package fasterxml.xml.databind.serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Custom serialization a StackTraceElement[] to JSON.
 */
class StackTraceSerializerTest {
    private final StringWriter writer = new StringWriter();
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        var module = new SimpleModule();
        module.addSerializer(StackTraceElement[].class, new StackTraceSerializer());
        mapper.registerModule(module);
    }

    @Test
    void stackIsFieldInPojo() throws IOException {
        var cause = new RuntimeException("cause message");
        cause.setStackTrace(new StackTraceElement[0]);

        var stackTrace = new StackTraceElement[]{
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        var throwable = new Throwable("my message", cause);
        throwable.setStackTrace(stackTrace);

        mapper.writeValue(writer, throwable);

        var actJson = writer.toString();
        System.out.println(actJson);

        var exp = "{" +
                  "message: 'my message', " +
                  "localizedMessage: 'my message', " +
                  "cause: {message: 'cause message', localizedMessage: 'cause message', cause: null, stackTrace: '', suppressed: []}," +
                  "stackTrace: 'my.Class.getName(file:1); my.Class2.getAge(file2:3)'," +
                  "suppressed: []}";

        assertThatJson(actJson).isEqualTo(exp);
    }

    @Test
    void stackIsObject() throws IOException {
        var stackTrace = new StackTraceElement[]{
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        mapper.writeValue(writer, stackTrace);

        var actJson = writer.toString();
        System.out.println(actJson);

        assertThatJson(actJson).isEqualTo("\"my.Class.getName(file:1); my.Class2.getAge(file2:3)\"");
    }

    @Test
    void stackIsEmptyArray() throws IOException {
        mapper.writeValue(writer, new StackTraceElement[0]);
        assertThat(writer).hasToString("\"\"");
    }

    @Test
    void stackIsNull() throws IOException {
        mapper.writeValue(writer, null);
        assertThat(writer).hasToString("null");
    }

    private static class StackTraceSerializer extends StdScalarSerializer<StackTraceElement[]> {

        StackTraceSerializer() {
            super(StackTraceElement[].class);
        }

        @Override
        public void serialize(StackTraceElement[] stack, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (stack != null) {
                var valueStr = Stream.of(stack).map(StackTraceElement::toString).collect(Collectors.joining("; "));
                gen.writeString(valueStr);
            } else {
                gen.writeNull();
            }
        }

    }
}
