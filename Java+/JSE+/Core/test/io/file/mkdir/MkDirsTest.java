package io.file.mkdir;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class MkDirsTest {

    /**
     * If invoke File#mkdirs on a file, it creates a directory instead of file.
     */
    @Test
    void mkdirsOnFile() throws IOException {
        var tmpDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
        tmpDir.deleteOnExit();

        var notExistsIntermediateDirs = new File(tmpDir, "a/b/file.txt");
        assertThat(notExistsIntermediateDirs).doesNotExist();

        assertThat(notExistsIntermediateDirs.mkdirs()).isTrue();

        assertThat(notExistsIntermediateDirs).exists();
        assertThat(notExistsIntermediateDirs).isDirectory();
        assertThat(notExistsIntermediateDirs.isFile()).isFalse();
    }
}