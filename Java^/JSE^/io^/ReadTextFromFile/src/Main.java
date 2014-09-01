import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws IOException {
        files();
    }

    private static void files() throws IOException {
        //write
        Path p = File.createTempFile("prefix-file_", ".suffix").toPath();
        List<String> expLines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, expLines);

        //read
        List<String> actLines = Files.readAllLines(p);
        out.println(actLines);
    }
}