import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class JimFsTest {
    @Test
    void test() throws IOException {
        try (var fs = Jimfs.newFileSystem(Configuration.unix())) {
            var foo = fs.getPath("/foo");
            Files.createDirectory(foo);

            var hello = foo.resolve("hello.txt");
            var expContent = "hello world";
            Files.write(hello, ImmutableList.of(expContent), UTF_8);

            var actContent = Files.readString(hello);
            assertThat(actContent).isEqualTo(expContent + "\n");
        }
    }
}
