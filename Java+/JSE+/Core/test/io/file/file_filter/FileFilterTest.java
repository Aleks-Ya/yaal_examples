package io.file.file_filter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class FileFilterTest {
    private static final File rootDir = new File("resourcesTest/io/file");

    @BeforeAll
    static void setUp() {
        assertThat(rootDir).exists();
    }

    @Test
    void onlyFiles() {
        var dir = new File(rootDir, "dirForWalk");
        var files = dir.listFiles(File::isFile);
        assertThat(files).isNotNull().contains(new File(dir, "story.txt"));
    }
}