package dbutils;

import com.databricks.dbutils_v1.DBUtilsHolder;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;

/**
 * Can work only in Databricks.
 */
class FileSystemIT {
    private final String dbfsTmpDir = "dbfs:/tmp/" + FileSystemIT.class.getSimpleName();
    private final FileSystem dbfs = DBUtilsHolder.dbutils().fs().dbfs();

    @Test
    void createDbfsFile() throws IOException {
        var dbfsPath = new Path(dbfsTmpDir);
        try (var dos = dbfs.create(dbfsPath)) {
            dos.writeBytes("Hello World!");
        }
    }

    @Test
    void copyFromLocalFile() throws IOException {
        var localFile = FileUtil.createTempFileWithContent("hello1").getAbsolutePath();
        var dbfsPath = new Path(dbfsTmpDir);
        var localPath = new Path(localFile);
        dbfs.copyFromLocalFile(localPath, dbfsPath);
    }
}