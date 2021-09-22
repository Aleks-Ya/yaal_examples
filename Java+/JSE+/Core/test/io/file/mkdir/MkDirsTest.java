package io.file.mkdir;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MkDirsTest {

    /**
     * If invoke File#mkdirs on a file, it creates a directory instead of file.
     */
    @Test
    void mkdirsOnFile() throws IOException {
        var tmpDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
        tmpDir.deleteOnExit();

        var notExistsIntermediateDirs = new File(tmpDir, "a/b/file.txt");
        assertThat(notExistsIntermediateDirs, not(anExistingFile()));

        assertTrue(notExistsIntermediateDirs.mkdirs());

        assertThat(notExistsIntermediateDirs, not(anExistingFile()));
        assertTrue(notExistsIntermediateDirs.isDirectory());
        assertFalse(notExistsIntermediateDirs.isFile());
    }
}