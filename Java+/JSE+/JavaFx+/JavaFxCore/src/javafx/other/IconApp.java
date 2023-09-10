package javafx.other;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class IconApp extends Application {
    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        var resource = Objects.requireNonNull(getClass().getResourceAsStream("icons8-decision-64.png"));
        var applicationIcon = new Image(resource);
        stage.getIcons().add(applicationIcon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}