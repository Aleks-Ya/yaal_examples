package watch;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;

public class WatchTest {

    @Test
    public void watchKey() throws IOException, InterruptedException {
        Path watchDir = Files.createTempDirectory("WatchTest_");
        WatchService service = FileSystems.getDefault().newWatchService();
        WatchKey key = watchDir.register(service, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        assertThat(key.pollEvents(), emptyIterable());

        Path newPath = Files.createTempFile(watchDir, "test_", ".tmp");
        Thread.sleep(1000);

        List<WatchEvent<?>> events = key.pollEvents();
        assertThat(events, hasSize(1));
        Path relativePath = (Path) events.get(0).context();
        Path changedPath = watchDir.resolve(relativePath);
        assertThat(changedPath, equalTo(newPath));

        System.out.println(newPath.toAbsolutePath());
    }

}
