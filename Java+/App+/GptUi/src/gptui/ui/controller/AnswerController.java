package gptui.ui.controller;

import gptui.Mdc;
import gptui.format.ClipboardHelper;
import gptui.gpt.QuestionApi;
import gptui.storage.AnswerState;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerType.GRAMMAR;
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
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    private static final Map<AnswerType, KeyCodeCombination> keyCodeCombinationMap = Map.of(
            SHORT, new KeyCodeCombination(DIGIT1, CONTROL_DOWN),
            LONG, new KeyCodeCombination(DIGIT2, CONTROL_DOWN));
    private static final Map<AnswerType, String> labelTextMap = Map.of(
            GRAMMAR, "Grammar\nanswer:",
            SHORT, "Short\nanswer:",
            LONG, "Long\nanswer:");
    private static final Map<AnswerType, Boolean> isCopyButtonDisabledMap = Map.of(
            GRAMMAR, true,
            SHORT, false,
            LONG, false);
    @FXML
    private Label answerLabel;
    @FXML
    private Circle statusCircle;
    @FXML
    private WebView webView;
    @FXML
    private Button copyButton;
    private AnswerType answerType;
    @Inject
    private ClipboardHelper clipboardHelper;
    @Inject
    private GptStorage storage;
    @Inject
    private QuestionApi questionApi;

    @FXML
    void clickCopyButton(ActionEvent ignoredEvent) {
        Mdc.run(answerType, () -> {
            log.trace("clickCopyButton");
            copyWebViewContentToClipboard();
        });
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        log.trace("onRegenerateButtonClick");
        questionApi.requestAnswer(model.getCurrentInteractionId(), answerType);
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        Mdc.run(answerType, () -> {
            log.trace("stageWasShowed");
            answerLabel.setText(labelTextMap.get(answerType));
            copyButton.setDisable(isCopyButtonDisabledMap.get(answerType));
            Optional.ofNullable(keyCodeCombinationMap.get(answerType))
                    .ifPresent(keyCodeCombination -> model.addAccelerator(keyCodeCombination, this::copyWebViewContentToClipboard));
        });
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        Mdc.run(answerType, () -> {
            log.trace("modelChanged later");
            Optional.ofNullable(model.getCurrentInteractionId())
                    .map(storage::readInteraction)
                    .map(Optional::orElseThrow)
                    .map(interaction -> interaction.getAnswer(answerType))
                    .ifPresentOrElse(answerOpt -> {
                        var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                        var state = answerOpt.isPresent() ? answerOpt.get().answerState() : NEW;
                        webView.getEngine().loadContent(html);
                        statusCircle.setFill(answerStateToColor(state));
                    }, () -> {
                        webView.getEngine().loadContent("");
                        statusCircle.setFill(WHITE);
                    });
        });
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    private void copyWebViewContentToClipboard() {
        var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
        clipboardHelper.putHtmlToClipboard(content);
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
