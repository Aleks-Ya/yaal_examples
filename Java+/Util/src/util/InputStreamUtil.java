package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Consumer;
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

    public static RedirectedOutput redirectStdOut() {
        return new RedirectedOutput(System.out, System::setOut);
    }

    public static RedirectedOutput redirectStdErr() {
        return new RedirectedOutput(System.err, System::setErr);
    }

    public static class RedirectedOutput implements AutoCloseable {
        private final PrintStream previousStream;
        private final ByteArrayOutputStream byteOS;
        private final Consumer<PrintStream> targetOut;

        public RedirectedOutput(PrintStream previousStream, Consumer<PrintStream> targetOut) {
            this.previousStream = previousStream;
            this.targetOut = targetOut;
            byteOS = new ByteArrayOutputStream();
            targetOut.accept(new PrintStream(byteOS));
        }

        @Override
        public void close() {
            targetOut.accept(previousStream);
        }

        @Override
        public String toString() {
            return byteOS.toString();
        }
    }

}
