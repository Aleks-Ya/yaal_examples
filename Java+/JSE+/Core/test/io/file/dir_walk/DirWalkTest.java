package io.file.dir_walk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirWalkTest {
    private static File rootDir;

    @BeforeAll
    static void setUp() {
        rootDir = new File("resourcesTest/io/file");
        assertTrue(rootDir.exists());
    }

    @Test
    void recursiveDirectoryList() {
        var files = RecursiveDirectoryList.findFiles(rootDir, ".*.xml");
        System.out.println(files);
        assertThat(files, containsInAnyOrder(
                new File(rootDir, "dirForWalk/subDir/config.xml"),
                new File(rootDir, "dirForWalk/subDir/subSubDir/context.xml")
        ));
    }

    @Test
    void contentDirectoryList() {
        var directoryList = new ContentDirectoryList(rootDir, ".*.xml");
        directoryList.process();
        var files = directoryList.getResult();
        assertThat(files, containsInAnyOrder(
                new File(rootDir, "dirForWalk/subDir"),
                new File(rootDir, "dirForWalk/subDir/subSubDir")
        ));
    }
}