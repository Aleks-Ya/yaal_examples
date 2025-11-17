package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static javafx.geometry.Pos.CENTER_LEFT;

public class ComboBoxApp extends Application {

    @Override
    public void start(Stage stage) {
        var cb1 = readOnlyComboBox();
        var cb2 = editableComboBox();
        var cb3 = addNewValueComboBox();
        var cb4 = buttonInComboBox();
        var cb5 = labelForComboBox();
        var cb6 = customItemRepresentation();
        var scene = new Scene(new VBox(cb1, new Separator(), cb2, new Separator(), cb3, new Separator(), cb4,
                new Separator(), cb5, new Separator(), cb6), 640, 480);
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
        comboBox.valueProperty().addListener((_, _, newValue) -> {
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

        comboBox.setCellFactory(_ -> new ListCell<>() {
            private final Button btn = new Button("❌");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(_ -> System.out.println("Button clicked for " + item));//Doesn't work
                    setText(item);
                    setGraphic(btn);
                }
            }
        });

        return comboBox;
    }

    private static HBox labelForComboBox() {
        var comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Water", "Air", "Earth", "Fire");
        var label = new Label("Choose _element:");
        label.setLabelFor(comboBox);
        label.setMnemonicParsing(true);
        var hBox = new HBox(label, comboBox);
        hBox.setAlignment(CENTER_LEFT);
        return hBox;
    }

    private static ComboBox<Person> customItemRepresentation() {
        var comboBox = new ComboBox<Person>();
        comboBox.getItems().addAll(new Person("John", 30), new Person("Mary", 25));
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Person person) {
                return person != null ? String.format("%s (%d)", person.name(), person.age()) : "---";
            }

            @Override
            public Person fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        return comboBox;
    }

    private record Person(String name, Integer age) {
    }
}