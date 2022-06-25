package io.file.rename_folder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

class EmptyDirTest {
    @Test
    void file() throws IOException {
        out.println("===== File#renameTo =====");
        var folder = Files.createTempDirectory(getClass().getSimpleName());
        var source = folder.toFile();
        out.printf("SOURCE: %s%n", source);

        var target = new File(source.getParent(), "renamed-file_" + source.getName());
        out.printf("TARGET: %s%n", target);

        var result = source.renameTo(target);
        out.printf("RESULT: %s%n%n", result);
    }
}