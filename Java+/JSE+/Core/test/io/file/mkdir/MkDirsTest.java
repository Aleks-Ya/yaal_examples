package io.file.mkdir;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MkDirsTest {

    /**
     * If invoke File#mkdirs on a file, it creates a directory instead of file.
     */
    @Test
    public void mkdirsOnFile() throws IOException {
        File tmpDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
        tmpDir.deleteOnExit();

        File notExistsIntermediateDirs = new File(tmpDir, "a/b/file.txt");
        assertThat(notExistsIntermediateDirs, not(anExistingFile()));

        assertTrue(notExistsIntermediateDirs.mkdirs());

        assertThat(notExistsIntermediateDirs, not(anExistingFile()));
        assertTrue(notExistsIntermediateDirs.isDirectory());
        assertFalse(notExistsIntermediateDirs.isFile());
    }
}