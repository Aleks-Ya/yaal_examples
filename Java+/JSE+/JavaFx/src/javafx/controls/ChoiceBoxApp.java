package javafx.controls;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChoiceBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var high = "High";
        var items = FXCollections.observableArrayList("Medium", high, "Low");
        var choiceBox = new ChoiceBox<>(items);
        choiceBox.setValue(high);
        var scene = new Scene(new StackPane(choiceBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}