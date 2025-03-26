package dbutils;

import com.databricks.dbutils_v1.DBUtilsHolder;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Can work only in Databricks.
 */
class DbUtilsTest {
    private final String tmpDir = "dbfs:/tmp/" + DbUtilsTest.class.getSimpleName();

    @Test
    void mkdir() {
        var dir = tmpDir + "/dir1";
        var created = DBUtilsHolder.dbutils().fs().mkdirs(dir);
        assertThat(created).isTrue();
    }

    @Test
    void dbfs() throws IOException {
        var dbfs = (FileSystem) DBUtilsHolder.dbutils().fs().dbfs();
        var path = new Path(tmpDir);
        try (var dos = dbfs.create(path)) {
            dos.writeBytes("Hello World!");
        }
    }

}