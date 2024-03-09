package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFieldApp extends Application {
    @Override
    public void start(Stage stage) {
        var textField = new TextField("Hello, JavaFX!");
        var focusedTextField = new TextField("Should be focused");
        var scene = new Scene(new VBox(textField, new Separator(), focusedTextField), 640, 480);
        stage.setScene(scene);
        stage.show();
        focusedTextField.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}