package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextAreaApp extends Application {
    @Override
    public void start(Stage stage) {
        var textArea1 = smallTextArea();
        var textArea2 = bigTextArea();
        var textArea3 = readOnlyTextArea();
        var textArea4 = wrapTextArea();
        var textArea5 = eventHandler();
        var scene = new Scene(new VBox(textArea1, textArea2, textArea3, textArea4, textArea5), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static TextArea smallTextArea() {
        return new TextArea("The JavaFX Documentation Project.");
    }

    private static TextArea bigTextArea() {
        var data = "The JavaFX Documentation Project aims to pull together useful information for JavaFX developers " +
                "from all over the web. The project is open source and encourages community participation to ensure " +
                "that the documentation is as highly polished and useful as possible.";
        return new TextArea(data);
    }

    private static TextArea readOnlyTextArea() {
        var textArea = new TextArea("The JavaFX Documentation Project.");
        textArea.setEditable(false);
        return textArea;
    }

    private static TextArea wrapTextArea() {
        var data = "The JavaFX Documentation Project aims to pull together useful information for JavaFX developers " +
                "from all over the web. The project is open source and encourages community participation to ensure " +
                "that the documentation is as highly polished and useful as possible.";
        var textArea = new TextArea(data);
        textArea.setWrapText(true);
        return textArea;
    }

    private static TextArea eventHandler() {
        var data = "Press Enter and look at the stdout";
        var textArea = new TextArea(data);
        textArea.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded() && change.getText().equals("\n")) {
                System.out.println("Enter key pressed!");
                return null;
            }
            return change;
        }));
        return textArea;
    }

    public static void main(String[] args) {
        launch();
    }
}