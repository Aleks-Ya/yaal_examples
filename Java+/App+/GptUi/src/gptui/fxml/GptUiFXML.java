package gptui.fxml;

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
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GptUiFXML extends Application {
    private static final Logger log = LoggerFactory.getLogger(GptUiFXML.class);
    private final GuiceContext context;
    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private Model model;

    public GptUiFXML() {
        this(new GuiceModule());
    }

    public GptUiFXML(Module module) {
        context = new GuiceContext(this, () -> List.of(module));
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.init();
        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("GptUi.fxml")));
        Parent root = fxmlLoader.load();
        var scene = new Scene(root, Color.LIGHTYELLOW);
        var version = readVersion();
        log.info("App version: " + version);
        stage.setTitle("GPT-4 Question Client v" + version);
        stage.setScene(scene);
        stage.setMaximized(true);
        var applicationIcon = new Image(requireNonNull(getClass().getClassLoader().getResourceAsStream("gptui/icon.png")));
        stage.getIcons().add(applicationIcon);
        stage.show();
        model.setScene(scene);
        model.fireStageShowed();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String readVersion() {
        try {
            var is = requireNonNull(getClass().getClassLoader().getResourceAsStream("gptui/version.txt"));
            try (var dataInputStream = new DataInputStream(is)) {
                var bytes = new byte[is.available()];
                dataInputStream.readFully(bytes);
                return new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
