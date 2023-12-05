package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var checkBox = new CheckBox("Tick me");
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                System.out.printf("Clicked: oldValue=%b, newValue=%b\n", oldValue, newValue));
        var scene = new Scene(new VBox(checkBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}