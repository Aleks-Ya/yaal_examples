import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class JimfsTest {
    @Test
    void test() throws IOException {
        var fs = Jimfs.newFileSystem(Configuration.unix());
        var foo = fs.getPath("/foo");
        Files.createDirectory(foo);

        var hello = foo.resolve("hello.txt");
        var expContent = "hello world";
        Files.write(hello, ImmutableList.of(expContent), StandardCharsets.UTF_8);

        var actContent = Files.readString(hello);
        assertThat(actContent).isEqualTo(expContent + "\n");
    }

}
