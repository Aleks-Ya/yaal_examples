package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

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

    public static Properties pathToProperties(Path path) {
        try {
            var properties = new Properties();
            properties.load(new FileInputStream(path.toFile()));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
