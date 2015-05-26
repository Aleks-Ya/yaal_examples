package input_stream_to_string;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Самый простой способ преобразовать InputStream в String.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final InputStream io = new ByteArrayInputStream("Привет!\nПока!".getBytes());
        String str = inputStreamToString(io, Charset.forName("UTF-8"));
        System.out.printf(">%s<", str);
    }

    private static String inputStreamToString(InputStream io, Charset charset) throws IOException {
        InputStreamReader isr = new InputStreamReader(io, charset);
        StringBuilder result = new StringBuilder();
        char[] c = new char[1];
        while (isr.read(c) != -1) {
            result.append(c);
        }
        return result.toString();
    }
}
