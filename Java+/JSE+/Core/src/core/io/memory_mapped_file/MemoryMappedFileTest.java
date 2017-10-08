package core.io.memory_mapped_file;

import org.junit.Test;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.net.URL;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MemoryMappedFileTest {
    @Test
    public void testMain() throws Exception {
        URL url = MemoryMappedFileTest.class.getResource("file.data");
        LineNumberReader reader2;
        try (LineNumberReader reader = Mockito.spy(new LineNumberReader(new FileReader(url.getFile())))) {
            reader2 = reader;
            System.out.println(reader.readLine());
        }
        Mockito.verify(reader2).close();
    }
}
