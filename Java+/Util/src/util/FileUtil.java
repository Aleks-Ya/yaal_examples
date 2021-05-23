package util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    private FileUtil() {
    }

    public static File createAbsentTempFile(String suffix) {
        try {
            var file = File.createTempFile(FileUtil.class.getSimpleName(), suffix);
            file.deleteOnExit();
            assert file.delete();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
