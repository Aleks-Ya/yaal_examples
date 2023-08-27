package gptui.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class GptView extends VBox {
    private final GptViewModel viewModel = new GptViewModel();
    private final ComboBox<String> themeComboBox = new ComboBox<>();
    private final TextArea questionTextArea = new TextArea();
    private final TextArea questionCorrectnessTextArea = new TextArea();
    private final TextArea shortAnswerTextArea = new TextArea();
    private final WebView longAnswerTextArea = new WebView();

    public GptView() {
        createView();
        bindViewModel();
    }

    private void createView() {
        var questionHistoryHBox = getQuestionHistoryHBox();
        var themeHBox = getThemeHBox();
        var questionHBox = getQuestionHBox();
        var questionCorrectnessHBox = getQuestionCorrectnessHBox();
        var shortAnswerHBox = getShortAnswerHBox();
        var longAnswerHBox = getLongAnswerHBox();
        getChildren().addAll(questionHistoryHBox, themeHBox, questionHBox, questionCorrectnessHBox, shortAnswerHBox,
                longAnswerHBox);
    }

    private void bindViewModel() {
        themeComboBox.valueProperty().bindBidirectional(viewModel.themeProperty());
        questionTextArea.textProperty().bindBidirectional(viewModel.questionProperty());
        questionCorrectnessTextArea.textProperty().bindBidirectional(viewModel.questionCorrectnessProperty());
        shortAnswerTextArea.textProperty().bindBidirectional(viewModel.shortAnswerProperty());
        viewModel.longAnswerProperty().addListener((observable, oldValue, newValue) -> {
            longAnswerTextArea.getEngine().loadContent(newValue);
        });
    }

    private static HBox getQuestionHistoryHBox() {
        var label = new Label("Question history");
        var high = "What's Apache Lucene?";
        var items = FXCollections.observableArrayList("What's Amazon Web Services?", high, "What's Docker?");
        var comboBox = new ComboBox<>(items);
        comboBox.setValue(high);
        return new HBox(label, comboBox);
    }

    private HBox getThemeHBox() {
        var label = new Label("Theme");
        var high = "Apache Lucene";
        themeComboBox.getItems().addAll("Amazon Web Services", high, "Docker", "Bible");
        themeComboBox.setValue(high);
        themeComboBox.setEditable(true);
        return new HBox(label, themeComboBox);
    }

    private HBox getQuestionHBox() {
        var label = new Label("Question");
        var button = new Button("Send");
        button.setMinWidth(70);
        button.setOnAction(this::sendQuestion);
        return new HBox(label, questionTextArea, button);
    }

    private HBox getQuestionCorrectnessHBox() {
        var label = new Label("Question correctness");
        questionCorrectnessTextArea.setEditable(false);
        questionCorrectnessTextArea.setWrapText(true);
        return new HBox(label, questionCorrectnessTextArea);
    }

    private HBox getShortAnswerHBox() {
        var label = new Label("Short answer");
        shortAnswerTextArea.setEditable(false);
        var copy = new Button("Copy");
        copy.setMinWidth(70);
        copy.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putString(shortAnswerTextArea.getText());
            clipboard.setContent(content);
        });
        return new HBox(label, shortAnswerTextArea, copy);
    }

    private HBox getLongAnswerHBox() {
        var label = new Label("Long answer");
//        longAnswerTextArea.setEditable(false);
        var copy = new Button("Copy");
        copy.setMinWidth(70);
        copy.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putHtml((String) longAnswerTextArea.getEngine().executeScript("document.documentElement.outerHTML"));
            clipboard.setContent(content);
        });
        return new HBox(label, longAnswerTextArea, copy);
    }

    private void sendQuestion(ActionEvent value) {
        viewModel.sendQuestion();
    }
}
