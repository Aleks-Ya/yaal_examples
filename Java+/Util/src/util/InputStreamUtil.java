package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.stream.Collectors;

public class InputStreamUtil {

    private InputStreamUtil() {
    }

    public static String inputStreamToString(InputStream is) {
        try {
            try (var isr = new InputStreamReader(is); var buffer = new BufferedReader(isr)) {
                return buffer.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ByteArrayOutputStream redirectStdOut() {
        var byteOS = new ByteArrayOutputStream();
        var printStream = new PrintStream(byteOS);
        System.setOut(printStream);
        return byteOS;
    }

    public static ByteArrayOutputStream redirectStdErr() {
        var byteOS = new ByteArrayOutputStream();
        var printStream = new PrintStream(byteOS);
        System.setErr(printStream);
        return byteOS;
    }

}
