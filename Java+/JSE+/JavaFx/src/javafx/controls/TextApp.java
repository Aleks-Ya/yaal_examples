package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextApp extends Application {
    @Override
    public void start(Stage stage) {
        var text = new Text("Hello, JavaFX");
        var scene = new Scene(new StackPane(text), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}