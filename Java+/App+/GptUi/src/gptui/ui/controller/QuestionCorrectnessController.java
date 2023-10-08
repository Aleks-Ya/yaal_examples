package gptui.ui.controller;

import gptui.storage.Answer;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.util.Optional;

import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;

class QuestionCorrectnessController extends BaseController {
    @FXML
    private WebView questionCorrectnessWebView;

    @Override
    public void modelChanged(Model model, EventSource source) {
        Optional.ofNullable(model.getCurrentInteraction())
                .map(interaction -> interaction.getAnswer(QUESTION_CORRECTNESS))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Answer::answerHtml)
                .ifPresent(html -> Platform.runLater(() -> questionCorrectnessWebView.getEngine().loadContent(html)));
    }
}

