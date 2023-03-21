package sardine.yandexdisk.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

record Settings(String username, String password, String folder, Path outputDir, int threadNumber) {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);

    public static Settings getSettings() {
        var props = new Properties();
        var homeDir = System.getProperty("user.home");
        var propertiesFile = Path.of(homeDir, ".yandex-disk-credentials", "webdav_forkjoin.properties").toString();
        try (var inputStream = new FileInputStream(propertiesFile)) {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var username = props.getProperty("username");
        var password = props.getProperty("password");
        var folder = props.getProperty("folder");
        if (!folder.startsWith("/")) {
            throw new IllegalArgumentException("Folder should start with '/': " + folder);
        }
        if (!folder.endsWith("/")) {
            throw new IllegalArgumentException("Folder should end with '/': " + folder);
        }
        var outputDir = Path.of(props.getProperty("output_dir"));
        var threadNumber = Integer.parseInt(props.getProperty("thread_number"));
        return new Settings(username, password, folder, outputDir, threadNumber);
    }

    public void print() {
        log.info("Username: {}, Password: {}, Folder: {}, Output dir: {}, Thread number: {}",
                username, "*".repeat(password.length()), folder, outputDir, threadNumber);
    }
}
