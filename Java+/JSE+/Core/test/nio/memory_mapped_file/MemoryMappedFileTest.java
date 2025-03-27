package nio.memory_mapped_file;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class MemoryMappedFileTest {
    @Test
    void memoryMappedFile() throws IOException {
        var url = getClass().getResource("file.data");
        try (var fis = new FileInputStream(requireNonNull(url).getFile());
             var channel = fis.getChannel()) {
            var buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            var bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            assertThat(new String(bytes, StandardCharsets.UTF_8)).isEqualTo("Happy Memory Mapped File");
        }
    }
}
