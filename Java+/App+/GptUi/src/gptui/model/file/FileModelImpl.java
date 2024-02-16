package gptui.model.file;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@Singleton
class FileModelImpl implements FileModel {
    private static final Logger log = LoggerFactory.getLogger(FileModelImpl.class);

    @Override
    public InputStream getAppIcon() {
        return requireNonNull(getClass().getResourceAsStream("icon.png"));
    }

    @Override
    public String getAppVersion() {
        log.info("Reading application version...");
        try {
            var is = requireNonNull(getClass().getClassLoader().getResourceAsStream("gptui/version.txt"));
            try (var dataInputStream = new DataInputStream(is)) {
                var bytes = new byte[is.available()];
                dataInputStream.readFully(bytes);
                var version = new String(bytes, StandardCharsets.UTF_8);
                log.info("Application version: {}", version);
                return version;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getFxmlLocation() {
        log.info("Java version: {}", Runtime.version());
        var gptUiFxml = getClass().getClassLoader().getResource("gptui/view/GptUi.fxml");
        log.info("GptUi.fxml: {}", gptUiFxml);
        return requireNonNull(gptUiFxml);
    }
}
