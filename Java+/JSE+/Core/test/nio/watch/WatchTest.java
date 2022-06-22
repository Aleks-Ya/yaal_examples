package nio.watch;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.assertj.core.api.Assertions.assertThat;

class WatchTest {

    @Test
    void watchKey() throws IOException, InterruptedException {
        var watchDir = Files.createTempDirectory("WatchTest_");
        var service = FileSystems.getDefault().newWatchService();
        var key = watchDir.register(service, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        assertThat(key.pollEvents()).isEmpty();

        var newPath = Files.createTempFile(watchDir, "test_", ".tmp");
        Thread.sleep(1000);

        var events = key.pollEvents();
        assertThat(events).hasSize(1);
        var relativePath = (Path) events.get(0).context();
        var changedPath = watchDir.resolve(relativePath);
        assertThat(changedPath).isEqualTo(newPath);

        System.out.println(newPath.toAbsolutePath());
    }

}
