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
        Path p = File.createTempFile("prefix-file_", ".suffix").toPath();
        out.println(p);

        List<String> lines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, lines);
    }
}