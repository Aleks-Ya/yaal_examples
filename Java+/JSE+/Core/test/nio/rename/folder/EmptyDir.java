package nio.rename.folder;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

class EmptyDir {

    @Test
    void files() throws IOException {
        out.println("===== Files#move =====");
        var source = Files.createTempDirectory("");
        out.printf("SOURCE: %s%n", source);

        var target = source.resolveSibling("renamed-files_" + source.getFileName());
        out.printf("TARGET: %s%n", target);

        var result = Files.move(source, target);
        out.printf("RESULT: %s%n%n", result);
    }
}