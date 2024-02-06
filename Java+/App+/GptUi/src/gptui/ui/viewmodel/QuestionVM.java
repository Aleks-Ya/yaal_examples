package gptui.ui.viewmodel;

import com.google.inject.Singleton;
import gptui.Mdc;
import gptui.storage.Interaction;
import gptui.storage.InteractionType;
import gptui.ui.model.ClipboardModel;
import gptui.ui.model.StateModel;
import gptui.ui.model.event.EventListener;
import gptui.ui.model.event.EventModel;
import gptui.ui.model.question.QuestionModel;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@Singleton
public class QuestionVM implements EventListener {
    private static final Logger log = LoggerFactory.getLogger(QuestionVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private EventModel eventModel;
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;

    @Inject
    public void init() {
        eventModel.subscribe(this);
    }

    @Override
    public void interactionChosenFromHistory() {
        log.trace("interactionChosenFromHistory");
        displayCurrentQuestionIfChanged();
    }

    private void displayCurrentQuestionIfChanged() {
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

    @Override
    public void themeWasChosen() {
        log.trace("themeWasChosen");
        if (stateModel.isHistoryFilteringEnabled()) {
            displayCurrentQuestionIfChanged();
        }
    }

    @Override
    public void stageWasShowed() {
        log.trace("stageWasShowed");
        addShortcuts();
    }

    private void addShortcuts() {
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
            questionModel.requestAnswer(interactionId, LONG);
            questionModel.requestAnswer(interactionId, SHORT);
            questionModel.requestAnswer(interactionId, GRAMMAR);
            questionModel.requestAnswer(interactionId, GCP);
        });
    }

    public void sendQuestion() {
        log.debug("sendQuestion");
        questionModel.sendQuestion(QUESTION, stateModel.getTemperatures());
    }

    public void sendDefinition() {
        log.debug("sendDefinition");
        questionModel.sendQuestion(DEFINITION, stateModel.getTemperatures());
    }

    public void sendGrammar() {
        log.debug("sendGrammar");
        questionModel.sendQuestion(InteractionType.GRAMMAR, stateModel.getTemperatures());
    }

    public void sendFact() {
        log.debug("sendFact");
        questionModel.sendQuestion(FACT, stateModel.getTemperatures());
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

