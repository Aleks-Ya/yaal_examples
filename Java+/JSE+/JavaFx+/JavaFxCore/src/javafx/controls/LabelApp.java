package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabelApp extends Application {
    @Override
    public void start(Stage stage) {
        var label1 = simpleLabel();
        var label2 = coloredLabel();
        var scene = new Scene(new VBox(label1, new Separator(), label2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Label simpleLabel() {
        return new Label("Hello, JavaFX");
    }

    private static Label coloredLabel() {
        var label = new Label("Hello, JavaFX");
        label.setStyle("-fx-background-color: red;");
        return label;
    }

    public static void main(String[] args) {
        launch();
    }
}