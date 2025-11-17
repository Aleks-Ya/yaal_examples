package javafx.other;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Copy and paste to the system clipboard.
 */
public class ClipboardApp extends Application {

    @Override
    public void start(Stage stage) {
        var textArea = new TextArea("Hello, JavaFX");
        var copyButton = new Button("Copy to clipboard");
        copyButton.setOnAction(_ -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putString(textArea.getText());
            clipboard.setContent(content);
        });
        var pasteButton = new Button("Paste from clipboard");
        pasteButton.setOnAction(_ -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = clipboard.getContent(DataFormat.PLAIN_TEXT);
            textArea.setText(content.toString());
        });
        var scene = new Scene(new VBox(textArea, copyButton, pasteButton), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}