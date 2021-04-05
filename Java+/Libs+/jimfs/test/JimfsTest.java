import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JimfsTest {
    @Test
    public void test() throws IOException {
        var fs = Jimfs.newFileSystem(Configuration.unix());
        var foo = fs.getPath("/foo");
        Files.createDirectory(foo);

        var hello = foo.resolve("hello.txt");
        var expContent = "hello world";
        Files.write(hello, ImmutableList.of(expContent), StandardCharsets.UTF_8);

        var actContent = Files.readString(hello);
        assertThat(actContent, equalTo(expContent + "\n"));
    }

}
