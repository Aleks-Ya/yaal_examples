package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.Mdc;
import gptui.model.clipboard.ClipboardModel;
import gptui.model.question.QuestionModel;
import gptui.model.state.StateModel;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionType;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static gptui.model.storage.InteractionType.DEFINITION;
import static gptui.model.storage.InteractionType.FACT;
import static gptui.model.storage.InteractionType.QUESTION;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

@Singleton
public class QuestionVM {
    private static final Logger log = LoggerFactory.getLogger(QuestionVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;
    @Inject
    private ViewModelMediator mediator;

    void displayCurrentQuestionIfChanged() {
        log.trace("displayCurrentQuestionIfChanged");
        stateModel.getCurrentInteractionOpt()
                .map(Interaction::question)
                .filter(question -> !question.equals(properties.questionTaText.getValue()))
                .ifPresent(question -> {
                    log.trace("Update question text: '{}'", question);
                    properties.questionTaText.setValue(question);
                    stateModel.setEditedQuestion(question);
                });
    }

    void displayCurrentQuestionIfChangedAndHistoryFiltered() {
        log.trace("themeWasChosen");
        if (stateModel.isHistoryFilteringEnabled()) {
            displayCurrentQuestionIfChanged();
        }
    }

    void addShortcuts() {
        log.trace("stageWasShowed");
        stateModel.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            setEditedQuestion();
            properties.questionTaFocused.setValue(false);
            properties.questionTaFocused.setValue(true);
            properties.questionTaPositionCaretToEnd.setValue(false);
            properties.questionTaPositionCaretToEnd.setValue(true);
        });
        stateModel.addAccelerator(new KeyCodeCombination(ESCAPE), () -> {
            log.debug("focusOnQuestion");
            properties.questionTaFocused.setValue(false);
            properties.questionTaFocused.setValue(true);
            properties.questionTaSelectAll.setValue(false);
            properties.questionTaSelectAll.setValue(true);
        });
        stateModel.addAccelerator(new KeyCodeCombination(ENTER, CONTROL_DOWN), () -> {
            log.debug("Send question by Ctrl-Enter");
            sendQuestion();
        });
    }

    public void onRegenerateButtonClick() {
        log.trace("onRegenerateButtonClick");
        var interactionId = stateModel.getCurrentInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Regenerate question: {}", interactionId);
            questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
            questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
            questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
            questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        });
    }

    public void sendQuestion() {
        log.debug("sendQuestion");
        var interactionId = questionModel.createNewInteraction(QUESTION);
        mediator.interactionCreated();
        mediator.currentInteractionChosen();
        questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
        questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
        questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
    }

    public void sendDefinition() {
        log.debug("sendDefinition");
        var interactionId = questionModel.createNewInteraction(DEFINITION);
        mediator.interactionCreated();
        questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
        questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
        questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
    }

    public void sendGrammar() {
        log.debug("sendGrammar");
        var interactionId = questionModel.createNewInteraction(InteractionType.GRAMMAR);
        mediator.interactionCreated();
        questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
        questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
        questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
    }

    public void sendFact() {
        log.debug("sendFact");
        var interactionId = questionModel.createNewInteraction(FACT);
        mediator.interactionCreated();
        questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
        questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
        questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
    }

    public void keyTypedQuestionTextArea() {
        log.trace("keyTypedQuestionTextArea");
        stateModel.setEditedQuestion(properties.questionTaText.getValue());
    }

    public void setEditedQuestion() {
        log.debug("pasteQuestionFromClipboardAndFocus");
        var question = clipboardModel.getTextFromClipboard();
        properties.questionTaText.setValue(question);
        stateModel.setEditedQuestion(question);
    }

    public static class Properties {
        public final StringProperty questionTaText = new SimpleStringProperty();
        public final BooleanProperty questionTaFocused = new SimpleBooleanProperty();
        public final BooleanProperty questionTaSelectAll = new SimpleBooleanProperty();
        public final BooleanProperty questionTaPositionCaretToEnd = new SimpleBooleanProperty();
    }
}

