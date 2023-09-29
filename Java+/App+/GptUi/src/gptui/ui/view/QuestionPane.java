package gptui.ui.view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

class QuestionPane extends HBox {
    private final TextArea textArea = new TextArea();

    public QuestionPane(Runnable sendMessageAction) {
        var label = new Label("Question:");
        label.setId("QuestionPaneLabel");
        var button = new Button("Send");
        button.setId("QuestionPaneButton");
        button.setMinWidth(70);
        button.setOnAction(event -> sendMessageAction.run());
        textArea.setId("QuestionPaneTextArea");
        textArea.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded() && change.getText().equals("\n")) {
                sendMessageAction.run();
                return null;
            }
            return change;
        }));
        getChildren().addAll(label, new Separator(), textArea, new Separator(), button);
        HBox.setHgrow(textArea, Priority.ALWAYS);
    }

    public StringProperty getTextProperty() {
        return textArea.textProperty();
    }

    public void setFocusOnTextArea() {
        textArea.requestFocus();
        var text = textArea.getText();
        var lastPosition = text != null ? text.length() : 0;
        textArea.positionCaret(lastPosition);
    }
}
