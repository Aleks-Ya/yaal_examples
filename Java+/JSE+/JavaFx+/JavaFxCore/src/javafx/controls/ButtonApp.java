package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ButtonApp extends Application {
    @Override
    public void start(Stage stage) {
        var button = new Button("Print to console");
        button.setOnAction((evt) -> System.out.println("Button was clicked"));
        var scene = new Scene(new StackPane(button), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}