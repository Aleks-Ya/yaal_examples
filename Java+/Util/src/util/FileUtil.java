package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

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

    public static File createTempDirectory(String suffix) {
        try {
            return Files.createTempDirectory(suffix).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fileToString(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
