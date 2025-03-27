package common3.filesystem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LocalFileSystemTest {

    @Test
    void createFile() throws IOException {
        var conf = new Configuration();
        var fs = FileSystem.getLocal(conf);
        var path = "/tmp/test_" + UUID.randomUUID();
        fs.create(new Path("file://" + path));
        assertThat(new File(path)).exists();
    }

}
