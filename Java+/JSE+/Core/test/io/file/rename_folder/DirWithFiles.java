package io.file.rename_folder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

public class DirWithFiles {
    @Test
    public void rename() throws IOException {
        var folder = Files.createTempDirectory(getClass().getSimpleName());
        out.println("===== File#renameTo =====");
        File source = folder.toFile();
        new File(source, "file.txt").createNewFile();
        out.printf("SOURCE: %s%n", source);

        File target = new File(source.getParent(), "renamed-file_" + source.getName());
        out.printf("TARGET: %s%n", target);

        boolean result = source.renameTo(target);
        out.printf("RESULT: %s%n%n", result);
    }
}