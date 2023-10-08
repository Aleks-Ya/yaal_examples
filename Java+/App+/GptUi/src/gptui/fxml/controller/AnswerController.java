package gptui.fxml.controller;

import gptui.format.ClipboardHelper;
import gptui.fxml.EventSource;
import gptui.fxml.Model;
import gptui.storage.AnswerState;
import gptui.storage.AnswerType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

import java.util.Optional;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

class AnswerController extends BaseController {
    @FXML
    private Circle statusCircle;
    @FXML
    private WebView webView;
    private AnswerType answerType;
    private KeyCodeCombination keyCodeCombination;

    @FXML
    void clickCopyButton(ActionEvent ignoredEvent) {
        copyWebViewContentToClipboard();
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        model.addAccelerator(keyCodeCombination, this::copyWebViewContentToClipboard);
    }

    private void copyWebViewContentToClipboard() {
        var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
        ClipboardHelper.putHtmlToClipboard(content);
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        Platform.runLater(() -> Optional.ofNullable(model.getCurrentInteraction())
                .map(interaction -> interaction.getAnswer(answerType))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(answer -> {
                    webView.getEngine().loadContent(answer.answerHtml());
                    statusCircle.setFill(answerStateToColor(answer.answerState()));
                }));
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public void setCopyAccelerator(KeyCodeCombination keyCodeCombination) {
        this.keyCodeCombination = keyCodeCombination;
    }

    private Color answerStateToColor(AnswerState answerState) {
        return switch (answerState) {
            case NEW -> WHITE;
            case SENT -> BLUE;
            case SUCCESS -> GREEN;
            case FAIL -> RED;
        };
    }
}
