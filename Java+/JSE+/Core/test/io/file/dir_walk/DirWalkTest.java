package io.file.dir_walk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DirWalkTest {
    private static File rootDir;

    @BeforeAll
    static void setUp() {
        rootDir = new File("resourcesTest/io/file");
        assertThat(rootDir).exists();
    }

    @Test
    void recursiveDirectoryList() {
        var files = RecursiveDirectoryList.findFiles(rootDir, ".*.xml");
        System.out.println(files);
        assertThat(files).containsExactlyInAnyOrder(
                new File(rootDir, "dirForWalk/subDir/config.xml"),
                new File(rootDir, "dirForWalk/subDir/subSubDir/context.xml")
        );
    }

    @Test
    void contentDirectoryList() {
        var directoryList = new ContentDirectoryList(rootDir, ".*.xml");
        directoryList.process();
        var files = directoryList.getResult();
        assertThat(files).containsExactlyInAnyOrder(
                new File(rootDir, "dirForWalk/subDir"),
                new File(rootDir, "dirForWalk/subDir/subSubDir")
        );
    }
}