package databricks;

import com.databricks.sdk.core.error.platform.ResourceDoesNotExist;
import com.databricks.sdk.mixin.DbfsExt;

import java.io.IOException;
import java.util.UUID;

public class DbfsHelper {
    private final DbfsExt dbfs;
    private final String tmpDir = "dbfs:/tmp/" + DbfsHelper.class.getSimpleName();

    public DbfsHelper(DbfsExt dbfs) {
        this.dbfs = dbfs;
    }

    public String getRootTmpDir() {
        return tmpDir;
    }

    public String getAbsentTmpDir() {
        return tmpDir + "/" + UUID.randomUUID();
    }

    public String getParentDir(String path) {
        return path.substring(0, path.lastIndexOf('/'));
    }

    public String getExistingTmpDir() {
        var dir = getAbsentTmpDir();
        dbfs.mkdirs(dir);
        return dir;
    }

    public String getAbsentTmpFile() {
        return getExistingTmpDir() + "/data.txt";
    }

    public Boolean isExist(String path) {
        try {
            dbfs.getStatus(path);
            return true;
        } catch (ResourceDoesNotExist e) {
            return false;
        }
    }

    public void writeFile(String path, String content) {
        try (var os = dbfs.getOutputStream(path)) {
            os.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String writeFile(String content) {
        var path = getAbsentTmpFile();
        writeFile(path, content);
        return path;
    }

    public byte[] readFile(String path) {
        try (var is = dbfs.open(path)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
