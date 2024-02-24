package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StageTitleApp extends Application {
    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.setTitle("The main window title");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}