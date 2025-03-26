package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            if (!file.delete()) throw new AssertionError();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static File createAbsentTempFile() {
        return createAbsentTempFile(FileUtil.class.getSimpleName());
    }

    public static File createTempFileWithContent(String content) {
        try {
            var file = createAbsentTempFile(FileUtil.class.getSimpleName());
            Files.writeString(file.toPath(), content);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createTempDirectoryPath(String suffix) {
        try {
            return Files.createTempDirectory(suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createTempDirectoryPath() {
        return createTempDirectoryPath(FileUtil.class.getSimpleName());
    }

    public static File createTempDirectoryFile(String suffix) {
        return createTempDirectoryPath(suffix).toFile();
    }

    public static File createTempDirectoryFile() {
        return createTempDirectoryPath().toFile();
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

    public static Properties homeDirFileToProperties(String... pathInUserHomeDir) {
        return pathToProperties(Paths.get(System.getProperty("user.home"), pathInUserHomeDir));
    }

    public static InputStream homeDirFileToIS(String... pathInUserHomeDir) {
        try {
            return new FileInputStream(Paths.get(System.getProperty("user.home"), pathInUserHomeDir).toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFileSilent(File file) {
        if (file.exists()) {
            var deleted = file.delete();
            if (!deleted) {
                throw new IllegalStateException("Cannot delete file: " + file.getAbsolutePath());
            }
        }
    }

    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }

}
