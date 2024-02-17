package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.Mdc;
import gptui.model.clipboard.ClipboardModel;
import gptui.model.question.QuestionModel;
import gptui.model.state.StateModel;
import gptui.model.storage.Answer;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionType;
import gptui.model.storage.StorageModel;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static gptui.model.storage.AnswerState.NEW;
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
    private StorageModel storage;
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;
    @Inject
    private ViewModelMediator mediator;

    void displayCurrentInteraction() {
        log.trace("displayCurrentInteraction");
        stateModel.getCurrentInteractionOpt()
                .map(Interaction::question)
                .filter(question -> !question.equals(properties.questionTaText.getValue()))
                .ifPresent(question -> {
                    log.trace("Update question text: '{}'", question);
                    properties.questionTaText.setValue(question);
                    stateModel.setEditedQuestion(question);
                });
    }

    void addShortcuts() {
        log.trace("addShortcuts");
        stateModel.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            pasteQuestionFromClipboard();
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
            onSendQuestionClick();
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

    public void onSendQuestionClick() {
        log.debug("onSendQuestionClick");
        createNewInteractionAndRequestAnswers(QUESTION);
        mediator.currentInteractionChosen();
    }

    public void onSendDefinitionClick() {
        log.debug("onSendDefinitionClick");
        createNewInteractionAndRequestAnswers(DEFINITION);
    }

    public void onSendGrammarClick() {
        log.debug("onSendGrammarClick");
        createNewInteractionAndRequestAnswers(InteractionType.GRAMMAR);
    }

    public void onSendFactClick() {
        log.debug("onSendFactClick");
        createNewInteractionAndRequestAnswers(FACT);
    }

    public void onKeyTypedQuestionTextArea() {
        log.trace("onKeyTypedQuestionTextArea");
        stateModel.setEditedQuestion(properties.questionTaText.getValue());
    }

    public void pasteQuestionFromClipboard() {
        log.debug("pasteQuestionFromClipboard");
        var question = clipboardModel.getTextFromClipboard();
        properties.questionTaText.setValue(question);
        stateModel.setEditedQuestion(question);
    }

    private synchronized void createNewInteractionAndRequestAnswers(InteractionType interactionType) {
        log.debug("createNewInteractionAndRequestAnswers: interactionType={}", interactionType);
        var interactionId = storage.newInteractionId();
        Mdc.run(interactionId, () -> {
            var theme = stateModel.getCurrentTheme();
            var question = stateModel.getEditedQuestion();
            var interaction = new Interaction(interactionId, interactionType, theme, question, Map.of(
                    GRAMMAR, new Answer(GRAMMAR, "", stateModel.getTemperature(GRAMMAR), "", "", NEW),
                    SHORT, new Answer(SHORT, "", stateModel.getTemperature(SHORT), "", "", NEW),
                    LONG, new Answer(LONG, "", stateModel.getTemperature(LONG), "", "", NEW),
                    GCP, new Answer(GCP, "", stateModel.getTemperature(GCP), "", "", NEW)
            ));
            storage.saveInteraction(interaction);
            stateModel.setCurrentInteractionId(interactionId);
            mediator.interactionCreated();
            questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
            questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
            questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
            questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
        });
    }

    public static class Properties {
        public final StringProperty questionTaText = new SimpleStringProperty();
        public final BooleanProperty questionTaFocused = new SimpleBooleanProperty();
        public final BooleanProperty questionTaSelectAll = new SimpleBooleanProperty();
        public final BooleanProperty questionTaPositionCaretToEnd = new SimpleBooleanProperty();
    }
}

