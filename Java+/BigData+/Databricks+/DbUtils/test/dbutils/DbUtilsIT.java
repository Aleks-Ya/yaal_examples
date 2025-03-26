package dbutils;

import com.databricks.backend.daemon.dbutils.FileInfo;
import com.databricks.dbutils_v1.DBUtilsHolder;
import com.databricks.dbutils_v1.DbfsUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Can work only in Databricks.
 */
class DbUtilsIT {
    private final String dbfsTmpDir = "dbfs:/tmp/" + DbUtilsIT.class.getSimpleName();
    private final DbfsUtils fs = DBUtilsHolder.dbutils().fs();

    @Test
    void listFile() {
        var files = fs.ls(dbfsTmpDir);
        files.foreach(FileInfo::path);
        assertThat(files.size()).isPositive();
    }

    @Test
    void createDirs() {
        var dir = dbfsTmpDir + "/dir1";
        var created = fs.mkdirs(dir);
        assertThat(created).isTrue();
    }

    @Test
    void deleteDirs() {
        var dir = dbfsTmpDir + "/dir1";
        var created = fs.rm(dir, true);
        assertThat(created).isTrue();
    }

}