package gptui.ui;

import gptui.storage.Interaction;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import static javafx.geometry.Orientation.VERTICAL;

public class GptView extends VBox {
    private final GptViewModel viewModel = new GptViewModel();
    private final ComboBox<Interaction> interactionHistoryComboBox = new ComboBox<>();
    private final ComboBox<String> themeComboBox = new ComboBox<>();
    private final TextArea questionTextArea = new TextArea();
    private final TextArea questionCorrectnessTextArea = new TextArea();
    private final WebView shortAnswerWebView = new WebView();
    private final WebView longAnswerWebView = new WebView();

    public GptView() {
        createView();
        bindViewModel();
    }

    private void createView() {
        var interactionHistoryHBox = createInteractionHistoryHBox();
        var themeHBox = createThemeHBox();
        var questionHBox = createQuestionHBox();
        var questionCorrectnessHBox = createQuestionCorrectnessHBox();
        var shortAnswerHBox = createShortAnswerHBox();
        var longAnswerHBox = createLongAnswerHBox();
        getChildren().addAll(interactionHistoryHBox, new Separator(VERTICAL),
                themeHBox, new Separator(VERTICAL),
                questionHBox, new Separator(VERTICAL),
                questionCorrectnessHBox, new Separator(VERTICAL),
                shortAnswerHBox, new Separator(VERTICAL),
                longAnswerHBox);
    }

    private void bindViewModel() {
        interactionHistoryComboBox.valueProperty().bindBidirectional(viewModel.interactionHistoryValueProperty());
        interactionHistoryComboBox.itemsProperty().bindBidirectional(viewModel.interactionHistoryItemsProperty());
        themeComboBox.valueProperty().bindBidirectional(viewModel.themeValueProperty());
        themeComboBox.itemsProperty().bindBidirectional(viewModel.themeItemsProperty());
        themeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !themeComboBox.getItems().contains(newValue)) {
                themeComboBox.getItems().add(newValue);
            }
        });
        questionTextArea.textProperty().bindBidirectional(viewModel.questionProperty());
        questionCorrectnessTextArea.textProperty().bindBidirectional(viewModel.questionCorrectnessProperty());
        viewModel.shortAnswerProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> shortAnswerWebView.getEngine().loadContent(newValue)));
        viewModel.longAnswerProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> longAnswerWebView.getEngine().loadContent(newValue)));
    }

    private HBox createInteractionHistoryHBox() {
        var label = new Label("Question history:");
        var sep = new Separator();
        return new HBox(label, sep, interactionHistoryComboBox);
    }

    private HBox createThemeHBox() {
        var label = new Label("Theme:");
        var sep = new Separator();
        themeComboBox.setEditable(true);
        return new HBox(label, sep, themeComboBox);
    }

    private HBox createQuestionHBox() {
        var label = new Label("Question:");
        var button = new Button("Send");
        button.setMinWidth(70);
        button.setOnAction(event -> sendQuestion());
        questionTextArea.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded() && change.getText().equals("\n")) {
                sendQuestion();
                return null;
            }
            return change;
        }));
        var hBox = new HBox(label, new Separator(), questionTextArea, new Separator(), button);
        HBox.setHgrow(questionTextArea, Priority.ALWAYS);
        return hBox;
    }

    private HBox createQuestionCorrectnessHBox() {
        var label = new Label("Question\ncorrectness:");
        questionCorrectnessTextArea.setEditable(false);
        questionCorrectnessTextArea.setWrapText(true);
        var sep = new Separator();
        var hBox = new HBox(label, sep, questionCorrectnessTextArea);
        HBox.setHgrow(questionCorrectnessTextArea, Priority.ALWAYS);
        return hBox;
    }

    private HBox createShortAnswerHBox() {
        var label = new Label("Short\nanswer:");
        var copy = new Button("Copy");
        copy.setMinWidth(70);
        copy.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putHtml((String) shortAnswerWebView.getEngine().executeScript("document.documentElement.outerHTML"));
            clipboard.setContent(content);
        });
        var hBox = new HBox(label, new Separator(), shortAnswerWebView, new Separator(), copy);
        HBox.setHgrow(shortAnswerWebView, Priority.ALWAYS);
        return hBox;
    }

    private HBox createLongAnswerHBox() {
        var label = new Label("Long\nanswer:");
        var copy = new Button("Copy");
        copy.setMinWidth(70);
        copy.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putHtml((String) longAnswerWebView.getEngine().executeScript("document.documentElement.outerHTML"));
            clipboard.setContent(content);
        });
        var hBox = new HBox(label, new Separator(), longAnswerWebView, new Separator(), copy);
        HBox.setHgrow(longAnswerWebView, Priority.ALWAYS);
        return hBox;
    }

    private void sendQuestion() {
        viewModel.sendQuestion();
    }
}
