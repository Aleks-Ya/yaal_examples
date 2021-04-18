package io.file.dir_walk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class DirWalkTest {
    private static File rootDir;

    @BeforeAll
    public static void setUp() {
        rootDir = new File("resourcesTest/io/file");
        assertTrue(rootDir.exists());
    }

    @Test
    public void recursiveDirectoryList() {
        List<File> files = RecursiveDirectoryList.findFiles(rootDir, ".*.xml");
        System.out.println(files);
        assertThat(files, containsInAnyOrder(
                new File(rootDir, "dirForWalk/subDir/config.xml"),
                new File(rootDir, "dirForWalk/subDir/subSubDir/context.xml")
        ));
    }

    @Test
    public void contentDirectoryList() {
        ContentDirectoryList directoryList = new ContentDirectoryList(rootDir, ".*.xml");
        directoryList.process();
        List<File> files = directoryList.getResult();
        assertThat(files, containsInAnyOrder(
                new File(rootDir, "dirForWalk/subDir"),
                new File(rootDir, "dirForWalk/subDir/subSubDir")
        ));
    }
}