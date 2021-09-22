package nio.walk;

import io.file.dir_walk.file_visitor.CollectFileNamesFileVisitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Walk through a directory with a FileVisitor.
 */
class CollectFileNamesFileVisitorTest {

    @Test
    void walkDir() throws IOException {
        var dir = Paths.get("resourcesTest/nio/walk");
        var visitor = new CollectFileNamesFileVisitor();
        Files.walkFileTree(dir, visitor);
        var fileNames = visitor.getFileNames();
        assertThat(fileNames).contains("content.xml", "content.xml", "novel.txt", "poem.txt");
    }
}