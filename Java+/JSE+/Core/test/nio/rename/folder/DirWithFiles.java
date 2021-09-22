package nio.rename.folder;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

class DirWithFiles {

    @Test
    void files() throws IOException {
        out.println("===== Files#move =====");
        var source = Files.createTempDirectory("");
        Files.createFile(source.resolve("my.txt"));
        out.printf("SOURCE: %s%n", source);

        var target = source.resolveSibling("rename-folder_target_files" + source.getFileName());
        out.printf("TARGET: %s%n", target);

        var result = Files.move(source, target);
        out.printf("RESULT: %s%n%n", result);
    }
}