package databricks.workspace;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.error.platform.ResourceDoesNotExist;
import com.databricks.sdk.mixin.DbfsExt;
import com.databricks.sdk.service.files.Delete;
import databricks.DbfsHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DbfsIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final DbfsExt dbfs = w.dbfs();
    private final DbfsHelper helper = new DbfsHelper(dbfs);

    @Test
    void list() {
        var dir = helper.getRootTmpDir();
        var files = dbfs.list(dir);
        assertThat(files).isNotEmpty();
        files.forEach(System.out::println);
    }

    @Test
    void listLocal() {
        var localDir = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/");
        assertThatThrownBy(() -> dbfs.list(localDir)).isInstanceOf(ResourceDoesNotExist.class);
    }

    @Test
    void recursiveList() {
        var dir = helper.getRootTmpDir();
        var files = dbfs.recursiveList(dir);
        assertThat(files).isNotEmpty();
        files.forEach(System.out::println);
    }

    @Test
    void getOutputStream() throws IOException {
        var path = helper.getAbsentTmpFile();
        var expContent = "abc".getBytes();
        try (var os = dbfs.getOutputStream(path)) {
            os.write(expContent);
        }
        var actContent = helper.readFile(path);
        assertThat(actContent).isEqualTo(expContent);
    }

    @Test
    void open() throws IOException {
        var expContent = "abc";
        var path = helper.writeFile(expContent);
        try (var is = dbfs.open(path)) {
            var bytes = is.readAllBytes();
            var actContent = new String(bytes);
            assertThat(actContent).isEqualTo(expContent);
        }
    }

    @Test
    void getStatus() {
        var path = helper.writeFile("abc");
        var status = dbfs.getStatus(path);
        System.out.println(status);
        assertThat(status.getIsDir()).isFalse();
        assertThat(status.getFileSize()).isEqualTo(3);
    }

    @Test
    void isDirExist() {
        var dir = helper.getAbsentTmpDir();
        assertThat(helper.isExist(dir)).isFalse();
        dbfs.mkdirs(dir);
        assertThat(helper.isExist(dir)).isTrue();
    }

    @Test
    void deleteFileExist() {
        var path = helper.writeFile("abc");
        assertThat(helper.isExist(path)).isTrue();
        var delete = new Delete().setPath(path);
        dbfs.delete(delete);
        assertThat(helper.isExist(path)).isFalse();
    }

    @Test
    void deleteFileAbsent() {
        var path = helper.getAbsentTmpFile();
        assertThat(helper.isExist(path)).isFalse();
        var delete = new Delete().setPath(path);
        dbfs.delete(delete);
        assertThat(helper.isExist(path)).isFalse();
    }

    @Test
    void mkdirs() {
        var dir = helper.getAbsentTmpDir();
        assertThat(helper.isExist(dir)).isFalse();
        dbfs.mkdirs(dir);
        assertThat(helper.isExist(dir)).isTrue();
        dbfs.mkdirs(dir);
        assertThat(helper.isExist(dir)).isTrue();
    }

    @Test
    void deleteDir() {
        var dir = helper.getExistingTmpDir();
        dbfs.mkdirs(dir);
        assertThat(helper.isExist(dir)).isTrue();
        var delete = new Delete().setPath(dir).setRecursive(true);
        dbfs.delete(delete);
        assertThat(helper.isExist(dir)).isFalse();
    }

    @Test
    void deleteDirRecursive() {
        var path = helper.writeFile("abc");
        var dir = helper.getParentDir(path);
        assertThat(helper.isExist(dir)).isTrue();
        assertThat(helper.isExist(path)).isTrue();
        var request = new Delete();
        request.setPath(dir);
        request.setRecursive(true);
        dbfs.delete(request);
        assertThat(helper.isExist(dir)).isFalse();
        assertThat(helper.isExist(path)).isFalse();
    }

    @Test
    void copyFileFromDbfsToLocal() throws IOException {
        var content = "abc";
        var dbfsPath = helper.writeFile(content);
        var localPath = FileUtil.createAbsentTempFile();
        assertThat(localPath).doesNotExist();
        try (var is = dbfs.open(dbfsPath);
             var os = new FileOutputStream(localPath)) {
            is.transferTo(os);
        }
        assertThat(localPath).exists().hasContent(content);
    }

    @Test
    @Disabled("""
            Does not work: java.nio.file.FileSystemNotFoundException: Provider "dbfs" not installed""")
    void write() throws IOException {
        var uri = URI.create(helper.getAbsentTmpFile());
        var path = Path.of(uri);
        var content = "Hello World 1!".getBytes();
        var actPath = dbfs.write(path, content);
        System.out.println(actPath);
    }
}