package lang.runtime;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ExecOsCommandTest {

    @Test
    void printStdOut() throws IOException {
        Process process = Runtime.getRuntime().exec("echo Hi!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
