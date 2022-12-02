package nio.path;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class PathExistsTest {
    @Test
    void exists() throws IOException {
        var fileExists = File.createTempFile("prefix-file_", ".suffix");
        var existsPath = fileExists.toPath();
        assertThat(Files.exists(existsPath)).isTrue();
        assertThat(Files.notExists(existsPath)).isFalse();

        var fileNotExists = new File("not_exists");
        var notExistsPath = fileNotExists.toPath();
        assertThat(Files.notExists(notExistsPath)).isTrue();
        assertThat(Files.exists(notExistsPath)).isFalse();
    }
}
