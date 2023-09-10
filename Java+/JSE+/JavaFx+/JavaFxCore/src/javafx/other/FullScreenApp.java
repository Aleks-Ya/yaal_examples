package javafx.other;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Start app in full-screen (without window header).
 */
public class FullScreenApp extends Application {
    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}