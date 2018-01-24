package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class InputStreamUtil {

    private InputStreamUtil() {
    }

    public static String inputStreamToString(InputStream is) {
        try {
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader buffer = new BufferedReader(isr)) {
                return buffer.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
