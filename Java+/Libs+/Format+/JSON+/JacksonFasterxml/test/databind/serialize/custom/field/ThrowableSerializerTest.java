package databind.serialize.custom.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Custom serialization a Throwable field to JSON.
 */
class ThrowableSerializerTest {

    @Test
    void test() throws IOException {
        var cause = new RuntimeException("cause message");
        cause.setStackTrace(new StackTraceElement[0]);

        var stackTrace = new StackTraceElement[]{
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        var throwable = new Throwable("my message", cause);
        throwable.setStackTrace(stackTrace);

        var data = new Data();
        data.throwable = throwable;


        var module = new SimpleModule();
        module.addSerializer(Throwable.class, new ThrowableSerializer());

        var mapper = new ObjectMapper();
        mapper.registerModule(module);

        var writer = new StringWriter();
        mapper.writeValue(writer, data);

        var exp = "{throwable: {" +
                "message: 'my message', " +
                "localizedMessage: 'my message', " +
                "cause: {message: 'cause message', localizedMessage: 'cause message', cause: null, stackTrace: '', suppressed: []}," +
                "stackTrace: 'my.Class.getName(file:1); my.Class2.getAge(file2:3)'," +
                "suppressed: []}" +
                "}";

        var actJson = writer.toString();
        System.out.println(actJson);
        assertJsonEquals(exp, actJson);
    }


    private static class Data {
        @JsonSerialize(using = ThrowableSerializer.class)
        Throwable throwable;
    }

    private static class ThrowableSerializer extends StdSerializer<Throwable> {

        ThrowableSerializer() {
            super(Throwable.class);
        }

        @Override
        public void serialize(Throwable value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();

            gen.writeStringField("message", value.getMessage());
            gen.writeStringField("localizedMessage", value.getLocalizedMessage());

            serializeCause(value, gen, provider);
            serializeStackTrace(value, gen, provider);
            serializeSuppresed(value, gen, provider);

            gen.writeEndObject();
        }

        private void serializeCause(Throwable throwable, JsonGenerator gen, SerializerProvider provider) throws IOException {
            var cause = throwable.getCause();
            if (cause != null) {
                gen.writeFieldName("cause");
                var serializer = provider.findValueSerializer(cause.getClass());
                serializer.serialize(cause, gen, provider);
            } else {
                gen.writeNullField("cause");
            }
        }

        private void serializeStackTrace(Throwable throwable, JsonGenerator gen, SerializerProvider provider) throws IOException {
            var cause = throwable.getStackTrace();
            if (cause != null) {
                gen.writeFieldName("stackTrace");
                var value = Stream.of(cause).map(StackTraceElement::toString).collect(Collectors.joining("; "));
                var serializer = provider.findValueSerializer(String.class);
                serializer.serialize(value, gen, provider);
            } else {
                gen.writeNullField("stackTrace");
            }
        }

        private void serializeSuppresed(Throwable throwable, JsonGenerator gen, SerializerProvider provider) throws IOException {
            var suppressed = throwable.getSuppressed();
            if (suppressed != null) {
                gen.writeFieldName("suppressed");
                var serializer = provider.findValueSerializer(suppressed.getClass());
                serializer.serialize(suppressed, gen, provider);
            } else {
                gen.writeNullField("suppressed");
            }
        }
    }
}
