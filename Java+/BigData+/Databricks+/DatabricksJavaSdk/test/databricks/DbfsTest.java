package databricks;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.mixin.DbfsExt;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DbfsTest {
    private final WorkspaceClient w = new WorkspaceClient();
    private final DbfsExt dbfs = w.dbfs();
    private final String tmpDir = "dbfs:/tmp/" + DbfsTest.class.getSimpleName();

    @Test
    void list() {
        var files = dbfs.list("/");
        files.forEach(System.out::println);
    }

    @Test
    void recursiveList() {
        var files = dbfs.recursiveList("/tmp");
        files.forEach(System.out::println);
    }

    @Test
    void getOutputStream() throws IOException {
        var path = "dbfs:/tmp/iabloal1_3.tmp";
        var content = "Hello World 1!".getBytes();
        try (var os = dbfs.getOutputStream(path)) {
            os.write(content);
        }
    }

    @Test
    void open() throws IOException {
        try (var is = dbfs.open("dbfs:/tmp/iabloal1_3.tmp")) {
            var bytes = is.readAllBytes();
            var content = new String(bytes);
            System.out.println(content);
        }
    }

    @Test
    void getStatus() {
        var status = dbfs.getStatus("dbfs:/tmp/iabloal1_3.tmp");
        System.out.println(status);
    }

    @Test
    void deleteFile() {
        dbfs.delete("dbfs:/tmp/iabloal1_3.tmp");
    }

    @Test
    void mkdirs() {
        dbfs.mkdirs("dbfs:/tmp/iabloal1/d1/d2");
    }

    @Test
    void deleteDir() {
        dbfs.delete("dbfs:/tmp/iabloal1/d1/d2");
    }

    @Test
    void copyFileFromDbfsToLocal() throws IOException {
        var dbfsPath = tmpDir + "/copyFileFromDbfsToLocal.tmp";
        var content = "Hello World 1!";
        try (var os = dbfs.getOutputStream(dbfsPath)) {
            os.write(content.getBytes());
        }
        var localPath = FileUtil.createAbsentTempFile();
        assertThat(localPath).doesNotExist();

        try (var is = dbfs.open(dbfsPath);
             var os = new FileOutputStream(localPath)) {
            IOUtils.copy(is, os);
        }
        assertThat(localPath).exists().hasContent(content);
    }

    @Test
    @Disabled("Does not work")
    void write() throws IOException {
        var uri = URI.create("dbfs:/tmp/iabloal1_2.tmp");
        var path = Path.of(uri);
        var content = "Hello World 1!".getBytes();
        var actPath = dbfs.write(path, content);
        System.out.println(actPath);
    }
}