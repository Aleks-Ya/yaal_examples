package util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    private FileUtil() {
    }

    public static File createAbsentTempFileDeleteOnExit(String suffix) {
        var file = createAbsentTempFile(suffix);
        file.deleteOnExit();
        return file;
    }

    public static File createAbsentTempFile(String suffix) {
        try {
            var file = File.createTempFile(FileUtil.class.getSimpleName(), suffix);
            assert file.delete();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
