package nio.walk;

import io.file.dir_walk.file_visitor.CollectFileNamesFileVisitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Обход диретории с помощью FileVisitor.
 */
public class CollectFileNamesFileVisitorTest {

    @Test
    public void test() throws IOException {
        Path dir = Paths.get("resourcesTest");
        CollectFileNamesFileVisitor visitor = new CollectFileNamesFileVisitor();
        Files.walkFileTree(dir, visitor);
        List<String> fileNames = visitor.getFileNames();
        assertThat(fileNames, contains("story.txt", "config.xml", "novel.txt", "context.xml"));
    }
}