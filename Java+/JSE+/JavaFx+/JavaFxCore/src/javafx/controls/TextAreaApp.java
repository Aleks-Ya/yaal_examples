package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextAreaApp extends Application {
    @Override
    public void start(Stage stage) {
        var textArea1 = smallTextArea();
        var textArea2 = bigTextArea();
        var textArea3 = readOnlyTextArea();
        var textArea4 = wrapTextArea();
        var textArea5 = eventHandlerByTextFormatter();
        var textArea6 = eventHandlerByEventDispatcher();
        var textArea7 = eventHandlerByEventFilter();
        var textArea8 = coloredTextArea();
        var scene = new Scene(new VBox(textArea1, textArea2, textArea3, textArea4, textArea5, textArea6, textArea7,
                textArea8), 640, 800);
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

    private static TextArea eventHandlerByTextFormatter() {
        var data = "Press Enter and look at the stdout (TextFormatter)";
        var textArea = new TextArea(data);
        textArea.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded() && change.getText().equals("\n")) {
                System.out.println("ENTER was pressed (TextFormatter)");
                return null;
            }
            return change;
        }));
        return textArea;
    }

    private static TextArea eventHandlerByEventDispatcher() {
        var data = "Press Enter and look at the stdout (EventDispatcher)";
        var textArea = new TextArea(data);
        textArea.setEventDispatcher((event, tail) -> {
            if (event instanceof KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
                    System.out.println("ENTER was pressed (EventDispatcher)");
                    return null;
                }
            }
            return tail.dispatchEvent(event);
        });
        return textArea;
    }

    private static TextArea eventHandlerByEventFilter() {
        var data = "Press Enter and look at the stdout (EventFilter)";
        var textArea = new TextArea(data);
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("ENTER was pressed (EventFilter)");
                event.consume();
            }
        });
        return textArea;
    }

    private static TextArea coloredTextArea() {
        var textArea = new TextArea("Colored Text Area");
        textArea.setStyle("-fx-control-inner-background: red;");
        return textArea;
    }

    public static void main(String[] args) {
        launch();
    }
}