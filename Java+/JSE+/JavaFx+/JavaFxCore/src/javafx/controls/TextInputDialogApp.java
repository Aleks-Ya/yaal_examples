package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextInputDialogApp extends Application {
    @Override
    public void start(Stage stage) {
        var dialog = dialog();
        var label = new Label();
        var button = new Button("Enter name");
        button.setOnAction(evt -> dialog.showAndWait().ifPresentOrElse(label::setText, () -> label.setText("<cancelled>")));
        var scene = new Scene(new VBox(button, new Separator(), label), 640, 300);
        stage.setScene(scene);
        stage.show();
    }

    private static TextInputDialog dialog() {
        var dialog = new TextInputDialog("John");
        dialog.setTitle("Name");
        dialog.setHeaderText("Enter your name:");
        return dialog;
    }

    public static void main(String[] args) {
        launch();
    }
}