package gptui;

import gptui.ui.GptView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

public class GptUiMain extends Application {
    private static final Logger log = LoggerFactory.getLogger(GptUiMain.class);

    @Override
    public void start(Stage stage) {
        log.info("Begin the start method");
        var version = readVersion();
        log.info("App version: " + version);
        var view = new GptView();
        var scene = new Scene(view, 640, 900);
        stage.setScene(scene);
        stage.setTitle("GPT-4 Question Client v" + version);
        stage.setMaximized(true);
        var applicationIcon = new Image(requireNonNull(getClass().getResourceAsStream("icon.png")));
        stage.getIcons().add(applicationIcon);
        stage.show();
        log.info("Finished the start method");
    }

    private String readVersion() {
        try {
            var is = requireNonNull(getClass().getResourceAsStream("version.txt"));
            try (var dataInputStream = new DataInputStream(is)) {
                var bytes = new byte[is.available()];
                dataInputStream.readFully(bytes);
                return new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        configureJavaUtilLogging();
        launch();
    }

    private static void configureJavaUtilLogging() {
        System.setProperty("jdk.httpclient.HttpClient.log", "ALL");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}