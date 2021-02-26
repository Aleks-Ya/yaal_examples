package databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Standard serialization a Throwable to JSON.
 */
public class ThrowableSerializationTest {

    @Test
    public void test() throws IOException, JSONException {
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
                "{methodName: 'getName', fileName: 'file', lineNumber: 1, className: 'my.Class', nativeMethod: false}," +
                "{methodName: 'getAge', fileName: 'file2', lineNumber: 3, className: 'my.Class2',nativeMethod: false}]" +
                "}";

        JSONAssert.assertEquals(exp, actJson, JSONCompareMode.STRICT);
    }
}
