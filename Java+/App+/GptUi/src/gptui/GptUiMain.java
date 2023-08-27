package gptui;

import gptui.ui.GptView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class GptUiMain extends Application {
    private static final Logger log = LoggerFactory.getLogger(GptUiMain.class);

    @Override
    public void start(Stage stage) {
        log.info("Begin the start method");
        var view = new GptView();
        var scene = new Scene(view, 640, 900);
        stage.setScene(scene);
        stage.setTitle("GPT-4 Question Client");
        stage.setMaximized(true);
        var applicationIcon = new Image(requireNonNull(getClass().getResourceAsStream("icon.png")));
        stage.getIcons().add(applicationIcon);
        stage.show();
        log.info("Finished the start method");
    }

    public static void main(String[] args) {
        System.setProperty("jdk.httpclient.HttpClient.log", "ALL");
        launch();
    }
}