package lang.runtime;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecOsCommand {

    @Test
    public void printStdOut() throws IOException {
        Process process = Runtime.getRuntime().exec("echo Hi!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}