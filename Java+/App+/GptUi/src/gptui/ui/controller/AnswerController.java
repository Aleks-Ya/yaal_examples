package gptui.ui.controller;

import gptui.format.ClipboardHelper;
import gptui.storage.AnswerState;
import gptui.storage.AnswerType;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class AnswerController extends BaseController {
    private static final Map<AnswerType, KeyCodeCombination> keyCodeCombinationMap = Map.of(
            SHORT, new KeyCodeCombination(DIGIT1, CONTROL_DOWN),
            LONG, new KeyCodeCombination(DIGIT2, CONTROL_DOWN));
    private static final Map<AnswerType, String> labelTextMap = Map.of(
            SHORT, "Short\nanswer:",
            LONG, "Long\nanswer:");
    @FXML
    private Label answerLabel;
    @FXML
    private Circle statusCircle;
    @FXML
    private WebView webView;
    private AnswerType answerType;
    @Inject
    private ClipboardHelper clipboardHelper;

    @FXML
    void clickCopyButton(ActionEvent ignoredEvent) {
        copyWebViewContentToClipboard();
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        answerLabel.setText(labelTextMap.get(answerType));
        model.addAccelerator(keyCodeCombinationMap.get(answerType), this::copyWebViewContentToClipboard);
    }

    private void copyWebViewContentToClipboard() {
        var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
        clipboardHelper.putHtmlToClipboard(content);
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        Optional.ofNullable(model.getCurrentInteraction())
                .map(interaction -> interaction.getAnswer(answerType))
                .ifPresentOrElse(answerOpt -> {
                    var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                    var state = answerOpt.isPresent() ? answerOpt.get().answerState() : AnswerState.NEW;
                    Platform.runLater(() -> {
                        webView.getEngine().loadContent(html);
                        statusCircle.setFill(answerStateToColor(state));
                    });
                }, () -> Platform.runLater(() -> {
                    webView.getEngine().loadContent("");
                    statusCircle.setFill(WHITE);
                }));
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
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
