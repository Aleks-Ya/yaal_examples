package nio.memory_mapped_file;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MemoryMappedFileTest {
    @Test
    public void memoryMappedFile() throws IOException {
        var url = getClass().getResource("file.data");
        try (var fis = new FileInputStream(url.getFile());
             var channel = fis.getChannel()) {
            var buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            var bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            assertThat(new String(bytes, StandardCharsets.UTF_8), equalTo("Happy Memory Mapped File"));
        }
    }
}
