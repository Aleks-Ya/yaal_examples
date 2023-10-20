package gptui.ui.controller;

import gptui.Mdc;
import gptui.gpt.QuestionApi;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionType;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.QUESTION;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class QuestionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Inject
    private GptStorage storage;
    @Inject
    private QuestionApi questionApi;
    @Inject
    private ClipboardHelper clipboardHelper;
    @FXML
    private TextArea questionTextArea;

    @Override
    public void initializeChild() {
        questionTextArea.addEventFilter(KEY_PRESSED, event -> {
            if (event.getCode() == ENTER) {
                log.debug("Send question by Enter");
                event.consume();
                questionApi.sendQuestion(QUESTION);
            }
        });
    }

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        log.debug("Send question by Send button");
        questionApi.sendQuestion(QUESTION);
    }

    @FXML
    void sendDefinition(ActionEvent ignoredEvent) {
        log.debug("Send definition by Send button");
        questionApi.sendQuestion(DEFINITION);
    }

    @FXML
    void sendGrammar(ActionEvent ignoredEvent) {
        log.debug("Send grammar by Send button");
        questionApi.sendQuestion(InteractionType.GRAMMAR);
    }

    @FXML
    public void sendFact(ActionEvent ignoredEvent) {
        log.debug("Send fact by Send button");
        questionApi.sendQuestion(FACT);
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        var interactionId = model.getCurrentInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Regenerate question: {}", interactionId);
            questionApi.requestAnswer(interactionId, LONG);
            questionApi.requestAnswer(interactionId, SHORT);
            questionApi.requestAnswer(interactionId, GRAMMAR);
        });
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        model.setEditedQuestion(questionTextArea.getText());
    }


    @Override
    public void interactionChosenFromHistory(Model model, EventSource source) {
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
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        model.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            var question = clipboardHelper.getTextFromClipboard();
            questionTextArea.setText(question);
            questionTextArea.requestFocus();
            questionTextArea.positionCaret(questionTextArea.getText().length());
            model.setEditedQuestion(question);
        });
        model.addAccelerator(new KeyCodeCombination(Q, CONTROL_DOWN), () -> questionApi.sendQuestion(QUESTION));
        model.addAccelerator(new KeyCodeCombination(D, CONTROL_DOWN), () -> questionApi.sendQuestion(DEFINITION));
        model.addAccelerator(new KeyCodeCombination(G, CONTROL_DOWN), () -> questionApi.sendQuestion(InteractionType.GRAMMAR));
        model.addAccelerator(new KeyCodeCombination(F, CONTROL_DOWN), () -> questionApi.sendQuestion(FACT));
    }
}

