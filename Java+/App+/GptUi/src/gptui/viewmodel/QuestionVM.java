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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static gptui.model.storage.InteractionType.DEFINITION;
import static gptui.model.storage.InteractionType.FACT;
import static gptui.model.storage.InteractionType.QUESTION;

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

    void focusOnQuestionAndSelect() {
        log.debug("focusOnQuestion");
        properties.questionTaFocused.setValue(false);
        properties.questionTaFocused.setValue(true);
        properties.questionTaSelectAll.setValue(false);
        properties.questionTaSelectAll.setValue(true);
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
        mediator.displayCurrentInteraction();
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

    void pasteQuestionFromClipboard() {
        log.debug("pasteQuestionFromClipboard");
        var question = clipboardModel.getTextFromClipboard();
        properties.questionTaText.setValue(question);
        stateModel.setEditedQuestion(question);
    }

    private synchronized void createNewInteractionAndRequestAnswers(InteractionType interactionType) {
        log.debug("createNewInteractionAndRequestAnswers: interactionType={}", interactionType);
        var interactionId = questionModel.createInteraction(interactionType);
        mediator.interactionCreated();
        questionModel.requestAnswer(interactionId, GCP, () -> mediator.answerUpdated(GCP));
        questionModel.requestAnswer(interactionId, LONG, () -> mediator.answerUpdated(LONG));
        questionModel.requestAnswer(interactionId, SHORT, () -> mediator.answerUpdated(SHORT));
        questionModel.requestAnswer(interactionId, GRAMMAR, () -> mediator.answerUpdated(GRAMMAR));
    }

    public static class Properties {
        public final StringProperty questionTaText = new SimpleStringProperty();
        public final BooleanProperty questionTaFocused = new SimpleBooleanProperty();
        public final BooleanProperty questionTaSelectAll = new SimpleBooleanProperty();
        public final BooleanProperty questionTaPositionCaretToEnd = new SimpleBooleanProperty();
    }
}

