package io.file.rename_folder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

public class EmptyDir {
    @Test
    public void file() throws IOException {
        out.println("===== File#renameTo =====");
        var folder = Files.createTempDirectory(getClass().getSimpleName());
        File source = folder.toFile();
        out.printf("SOURCE: %s%n", source);

        File target = new File(source.getParent(), "renamed-file_" + source.getName());
        out.printf("TARGET: %s%n", target);

        boolean result = source.renameTo(target);
        out.printf("RESULT: %s%n%n", result);
    }
}