package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TextFieldApp extends Application {
    @Override
    public void start(Stage stage) {
        var textField = new TextField("Hello, JavaFX!");
        var scene = new Scene(new StackPane(textField), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}