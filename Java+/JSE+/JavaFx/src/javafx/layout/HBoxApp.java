package javafx.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var text1 = new Text("Hello, JavaFX");
        var text2 = new Text("Bye, Everyone");
        var box = new HBox(text1, text2);
        var scene = new Scene(new StackPane(box), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}