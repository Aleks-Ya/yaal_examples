package databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Standard serialization a Throwable to JSON.
 */
public class ThrowableSerializationTest {

    @Test
    public void test() throws IOException {
        var cause = new RuntimeException("cause message");
        cause.setStackTrace(new StackTraceElement[0]);

        StackTraceElement[] stackTrace = {
                new StackTraceElement("my.Class", "getName", "file", 1),
                new StackTraceElement("my.Class2", "getAge", "file2", 3)
        };

        var throwable = new Throwable("my message", cause);
        throwable.setStackTrace(stackTrace);

        var mapper = new ObjectMapper();

        var writer = new StringWriter();
        mapper.writeValue(writer, throwable);

        var actJson = writer.toString();
        System.out.println(actJson);

        var exp = "{" +
                "message: 'my message', " +
                "localizedMessage: 'my message', " +
                "cause: {message: 'cause message', localizedMessage: 'cause message', cause: null, stackTrace: [], suppressed: []}," +
                "suppressed: []," +
                "stackTrace: [" +
                "{classLoaderName:null, moduleName:null, moduleVersion:null, methodName: 'getName', fileName: 'file', lineNumber: 1, className: 'my.Class', nativeMethod: false}," +
                "{classLoaderName:null, moduleName:null, moduleVersion:null, methodName: 'getAge', fileName: 'file2', lineNumber: 3, className: 'my.Class2',nativeMethod: false}]" +
                "}";

        assertJsonEquals(exp, actJson);
    }
}
