package nio.rename.folder;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class EmptyDir {

    @Test
    public void files() throws IOException {
        out.println("===== Files#move =====");
        Path source = Files.createTempDirectory("");
        out.printf("SOURCE: %s%n", source);

        Path target = source.resolveSibling("renamed-files_" + source.getFileName());
        out.printf("TARGET: %s%n", target);

        Path result = Files.move(source, target);
        out.printf("RESULT: %s%n%n", result);
    }
}