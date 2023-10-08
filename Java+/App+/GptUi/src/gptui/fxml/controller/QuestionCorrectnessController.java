package gptui.fxml.controller;

import gptui.fxml.EventSource;
import gptui.fxml.Model;
import gptui.storage.Answer;
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
        Platform.runLater(() -> Optional.ofNullable(model.getCurrentInteraction())
                .map(interaction -> interaction.getAnswer(QUESTION_CORRECTNESS))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Answer::answerHtml)
                .ifPresent(html -> questionCorrectnessWebView.getEngine().loadContent(html)));
    }
}

