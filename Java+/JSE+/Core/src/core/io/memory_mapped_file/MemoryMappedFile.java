package core.io.memory_mapped_file;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Memory-mapped files usage.
 * User: Aleks
 * Date: 03.10.13
 */
public class MemoryMappedFile {

    @Test
    public void main() throws IOException {
        URL url = MemoryMappedFile.class.getResource("file.data");
        try (FileInputStream fis = new FileInputStream(url.getFile());
             FileChannel channel = fis.getChannel()) {
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            System.out.print(new String(bytes, Charset.forName("UTF-8")));
        }
    }
}
