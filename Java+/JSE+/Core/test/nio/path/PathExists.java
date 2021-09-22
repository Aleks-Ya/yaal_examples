package nio.path;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathExists {
    @Test
    void exists() throws IOException {
        var fileExists = File.createTempFile("prefix-file_", ".suffix");
        var existsPath = fileExists.toPath();
        assertTrue(Files.exists(existsPath));
        assertFalse(Files.notExists(existsPath));

        var fileNotExists = new File("not_exists");
        var notExistsPath = fileNotExists.toPath();
        assertTrue(Files.notExists(notExistsPath));
        assertFalse(Files.exists(notExistsPath));
    }
}
