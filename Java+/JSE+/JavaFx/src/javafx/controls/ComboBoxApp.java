package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComboBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var comboBox1 = readOnlyComboBox();
        var comboBox2 = editableComboBox();
        var comboBox3 = addNewValueComboBox();
        var scene = new Scene(new VBox(comboBox1, comboBox2, comboBox3), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static ComboBox<Object> readOnlyComboBox() {
        var comboBox = new ComboBox<>();
        var defaultOption = "Sea";
        comboBox.getItems().addAll(defaultOption, "Air", "Space");
        comboBox.setValue(defaultOption);
        return comboBox;
    }

    private static ComboBox<Object> editableComboBox() {
        var comboBox = new ComboBox<>();
        var defaultOption = "Option 1";
        comboBox.getItems().addAll(defaultOption, "Option 2", "Option 3");
        comboBox.setEditable(true);
        comboBox.setValue(defaultOption);
        return comboBox;
    }

    private static ComboBox<Object> addNewValueComboBox() {
        var comboBox = new ComboBox<>();
        var defaultOption = "Option 1";
        comboBox.getItems().addAll(defaultOption, "Option 2", "Option 3");
        comboBox.setEditable(true);
        comboBox.setValue(defaultOption);
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !comboBox.getItems().contains(newValue)) {
                System.out.println("Custom value entered: " + newValue);
                comboBox.getItems().add(newValue);
            } else {
                System.out.println("Existing value was chose: " + newValue);
            }
        });
        return comboBox;
    }

    public static void main(String[] args) {
        launch();
    }
}