package controlsfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * Not work.
 */
public class TextFieldsBindAutoCompletionApp extends Application {
    @Override
    public void start(Stage stage) {
        var list = FXCollections.observableArrayList("Medium", "High", "Low");
        var comboBoxEditor = new ComboBox<>(list);
        TextFields.bindAutoCompletion(comboBoxEditor.getEditor(), list);
        var scene = new Scene(new VBox(comboBoxEditor), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}