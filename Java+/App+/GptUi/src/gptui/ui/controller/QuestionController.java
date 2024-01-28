package gptui.ui.controller;

import gptui.Mdc;
import gptui.gpt.QuestionApi;
import gptui.storage.Interaction;
import gptui.storage.InteractionStorage;
import gptui.storage.InteractionType;
import gptui.ui.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import java.util.Optional;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.QUESTION;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

public class QuestionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Inject
    private InteractionStorage storage;
    @Inject
    private QuestionApi questionApi;
    @Inject
    private ClipboardHelper clipboardHelper;
    @FXML
    private TextArea questionTextArea;

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        log.debug("sendQuestion");
        questionApi.sendQuestion(QUESTION, model.getTemperatures());
    }

    @FXML
    void sendDefinition(ActionEvent ignoredEvent) {
        log.debug("sendDefinition");
        questionApi.sendQuestion(DEFINITION, model.getTemperatures());
    }

    @FXML
    void sendGrammar(ActionEvent ignoredEvent) {
        log.debug("sendGrammar");
        questionApi.sendQuestion(InteractionType.GRAMMAR, model.getTemperatures());
    }

    @FXML
    public void sendFact(ActionEvent ignoredEvent) {
        log.debug("sendFact");
        questionApi.sendQuestion(FACT, model.getTemperatures());
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        log.trace("onRegenerateButtonClick");
        var interactionId = model.getCurrentInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Regenerate question: {}", interactionId);
            questionApi.requestAnswer(interactionId, LONG);
            questionApi.requestAnswer(interactionId, SHORT);
            questionApi.requestAnswer(interactionId, GRAMMAR);
            questionApi.requestAnswer(interactionId, GCP);
        });
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        log.trace("keyTypedQuestionTextArea");
        model.setEditedQuestion(questionTextArea.getText());
    }


    @Override
    public void interactionChosenFromHistory(Model model) {
        log.trace("interactionChosenFromHistory");
        Optional.ofNullable(model.getCurrentInteractionId())
                .map(storage::readInteraction)
                .map(Optional::orElseThrow)
                .map(Interaction::question)
                .filter(question -> !question.equals(questionTextArea.getText()))
                .ifPresent(question -> {
                    questionTextArea.setText(question);
                    model.setEditedQuestion(question);
                });
    }

    @Override
    public void stageWasShowed(Model model) {
        log.trace("stageWasShowed");
        model.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            var question = clipboardHelper.getTextFromClipboard();
            questionTextArea.setText(question);
            questionTextArea.requestFocus();
            questionTextArea.positionCaret(questionTextArea.getText().length());
            model.setEditedQuestion(question);
        });
        model.addAccelerator(new KeyCodeCombination(ESCAPE), () -> {
            log.debug("focusOnQuestion");
            questionTextArea.requestFocus();
            questionTextArea.selectAll();
        });
        model.addAccelerator(new KeyCodeCombination(ENTER, CONTROL_DOWN), () -> {
            log.debug("Send question by Ctrl-Enter");
            questionApi.sendQuestion(QUESTION, model.getTemperatures());
        });
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}

