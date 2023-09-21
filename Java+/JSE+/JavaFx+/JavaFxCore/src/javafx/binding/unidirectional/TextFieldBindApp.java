package javafx.binding.unidirectional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFieldBindApp extends Application {
    @Override
    public void start(Stage stage) {
        var textField1 = new TextField();
        var textField2 = new TextField();
        textField2.textProperty().bind(textField1.textProperty()); //textField2 updates textField1
        var scene = new Scene(new VBox(textField1, textField2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}