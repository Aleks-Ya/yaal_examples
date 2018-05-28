import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class JimfsTest {
    @Test
    public void test() throws IOException {
        // For a simple file system with Unix-style paths and behavior:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        Path foo = fs.getPath("/foo");
        Files.createDirectory(foo);

        Path hello = foo.resolve("hello.txt"); // /foo/hello.txt
        Files.write(hello, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
    }

}
