package nio.path;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathExists {
    @Test
    public void exists() throws IOException {
        File fileExists = File.createTempFile("prefix-file_", ".suffix");
        Path existsPath = fileExists.toPath();
        assertTrue(Files.exists(existsPath));
        assertFalse(Files.notExists(existsPath));

        File fileNotExists = new File("not_exists");
        Path notExistsPath = fileNotExists.toPath();
        assertTrue(Files.notExists(notExistsPath));
        assertFalse(Files.exists(notExistsPath));
    }
}
