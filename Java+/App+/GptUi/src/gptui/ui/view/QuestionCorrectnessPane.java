package gptui.ui.view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

class QuestionCorrectnessPane extends HBox {
    private final TextArea textArea = new TextArea();

    public QuestionCorrectnessPane() {
        var label = new Label("Question\ncorrectness:");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        var sep = new Separator();
        getChildren().addAll(label, sep, textArea);
        HBox.setHgrow(textArea, Priority.ALWAYS);
    }

    public StringProperty getTextProperty() {
        return textArea.textProperty();
    }
}
