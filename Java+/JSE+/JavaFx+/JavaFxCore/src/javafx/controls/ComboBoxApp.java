package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComboBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var comboBox1 = readOnlyComboBox();
        var comboBox2 = editableComboBox();
        var comboBox3 = addNewValueComboBox();
        var comboBox4 = buttonInComboBox();
        var scene = new Scene(new VBox(comboBox1, comboBox2, comboBox3, comboBox4), 640, 480);
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

    private static ComboBox<String> buttonInComboBox() {
        var comboBox = new ComboBox<String>();
        var defaultOption = "Option 1";
        comboBox.getItems().addAll(defaultOption, "Option 2", "Option 3");
        comboBox.setEditable(true);
        comboBox.setValue(defaultOption);

        comboBox.setCellFactory(param -> new ListCell<>() {
            private final Button btn = new Button("❌");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> System.out.println("Button clicked for " + item));//Doesn't work
                    setText(item);
                    setGraphic(btn);
                }
            }
        });

        return comboBox;
    }

    public static void main(String[] args) {
        launch();
    }
}