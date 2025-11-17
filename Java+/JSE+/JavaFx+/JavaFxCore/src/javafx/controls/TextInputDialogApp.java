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
        var dialogFocused = dialogFocused();
        var label = new Label();
        var button = button(dialog, label);
        var buttonFocused = buttonFocused(dialogFocused, label);
        var scene = new Scene(new VBox(button, new Separator(), buttonFocused, new Separator(), label), 640, 300);
        stage.setScene(scene);
        stage.show();
    }

    private static Button buttonFocused(TextInputDialog dialog, Label label) {
        var button = new Button("Enter name in a focused dialog");
        button.setOnAction(_ -> {
            dialog.show();
            dialog.getEditor().requestFocus();
            dialog.hide();
            dialog.showAndWait().ifPresentOrElse(
                    value -> label.setText("Focused: " + value),
                    () -> label.setText("Focused: <cancelled>"));
        });
        return button;
    }

    private static Button button(TextInputDialog dialog, Label label) {
        var button = new Button("Enter name");
        button.setOnAction(_ -> dialog.showAndWait()
                .ifPresentOrElse(label::setText, () -> label.setText("<cancelled>")));
        return button;
    }

    private static TextInputDialog dialog() {
        var dialog = new TextInputDialog("John");
        dialog.setTitle("Name");
        dialog.setHeaderText("Enter your name:");
        return dialog;
    }

    private static TextInputDialog dialogFocused() {
        var dialog = new TextInputDialog("John");
        dialog.setTitle("Focused dialog");
        dialog.setHeaderText("Enter your name:");
        return dialog;
    }

}