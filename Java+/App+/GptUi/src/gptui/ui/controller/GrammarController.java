package gptui.ui.controller;

import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.util.Optional;

import static gptui.storage.AnswerType.GRAMMAR;

public class GrammarController extends BaseController {
    @FXML
    private WebView grammarWebView;

    @Override
    public void modelChanged(Model model, EventSource source) {
        Optional.ofNullable(model.getCurrentInteraction())
                .map(interaction -> interaction.getAnswer(GRAMMAR))
                .ifPresentOrElse(answerOpt -> {
                    var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                    Platform.runLater(() -> grammarWebView.getEngine().loadContent(html));
                }, () -> Platform.runLater(() -> grammarWebView.getEngine().loadContent("")));
    }
}

