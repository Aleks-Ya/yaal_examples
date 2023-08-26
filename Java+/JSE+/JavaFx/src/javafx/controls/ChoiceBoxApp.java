package javafx.controls;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChoiceBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var choiceBox1 = fillUsingFxCollection();
        var choiceBox2 = fillUsingGetItems();
        var scene = new Scene(new VBox(choiceBox1, choiceBox2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static ChoiceBox<String> fillUsingFxCollection() {
        var high = "High";
        var items = FXCollections.observableArrayList("Medium", high, "Low");
        var choiceBox = new ChoiceBox<>(items);
        choiceBox.setValue(high);
        return choiceBox;
    }

    private static ChoiceBox<String> fillUsingGetItems() {
        var choiceBox = new ChoiceBox<String>();
        var defaultItem = "Red";
        choiceBox.getItems().add(defaultItem);
        choiceBox.getItems().add("Blue");
        choiceBox.getItems().add("Green");
        choiceBox.setValue(defaultItem);
        return choiceBox;
    }

    public static void main(String[] args) {
        launch();
    }
}