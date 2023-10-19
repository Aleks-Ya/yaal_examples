package gptui.ui;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GptUiApplication extends Application implements EventSource {
    private static final Logger log = LoggerFactory.getLogger(GptUiApplication.class);
    private final GuiceContext context;
    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private Model model;

    public GptUiApplication() {
        this(new GuiceModule());
    }

    public GptUiApplication(Module module) {
        context = new GuiceContext(this, () -> List.of(module));
        context.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Java version: {}", Runtime.version());
        var gptUiFxml = getClass().getResource("GptUi.fxml");
        log.info("GptUi.fxml: {}", gptUiFxml);
        fxmlLoader.setLocation(requireNonNull(gptUiFxml));
        Parent root = fxmlLoader.load();
        var scene = new Scene(root, Color.LIGHTYELLOW);
        var version = readVersion();
        stage.setTitle("GPT-4 Question Client v" + version);
        stage.setScene(scene);
        stage.setMaximized(true);
        var applicationIcon = readApplicationIcon();
        stage.getIcons().add(applicationIcon);
        stage.show();
        model.setScene(scene);
        model.fireStageShowed(this);
    }

    public GuiceContext getGuiceContext() {
        return context;
    }

    private Image readApplicationIcon() {
        log.info("Loading application icon...");
        var applicationIcon = new Image(requireNonNull(getClass().getResourceAsStream("icon.png")));
        log.info("Application icon: {}", applicationIcon);
        return applicationIcon;
    }

    private String readVersion() {
        log.info("Reading application version...");
        try {
            var is = requireNonNull(getClass().getResourceAsStream("version.txt"));
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
}
