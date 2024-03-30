package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.Mdc;
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
import static gptui.viewmodel.Styles.QUESTION_STYLE_EDITED;

@Singleton
public class QuestionVM {
    private static final Logger log = LoggerFactory.getLogger(QuestionVM.class);
    public final Properties properties = new Properties();
    @Inject
    private ViewModelMediator mediator;

    void displayCurrentInteraction() {
        log.trace("displayCurrentInteraction");
        mediator.getCurrentInteractionOpt()
                .map(Interaction::question)
                .filter(question -> !question.equals(properties.questionTaText.getValue()))
                .ifPresent(question -> {
                    log.trace("Update question text: '{}'", question);
                    properties.questionTaText.setValue(question);
                    mediator.setEditedQuestion(question);
                    updateQuestionTextAreaBackgroundColor();
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
        var interactionId = mediator.getCurrentInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Regenerate question: {}", interactionId);
            mediator.requestAnswer(interactionId, LONG);
            mediator.requestAnswer(interactionId, SHORT);
            mediator.requestAnswer(interactionId, GRAMMAR);
            mediator.requestAnswer(interactionId, GCP);
        });
    }

    public void onSendQuestionClick() {
        log.debug("onSendQuestionClick");
        createNewInteractionAndRequestAnswers(QUESTION);
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
        mediator.setEditedQuestion(properties.questionTaText.getValue());
        updateQuestionTextAreaBackgroundColor();
    }

    private void updateQuestionTextAreaBackgroundColor() {
        if (mediator.isEnteringNewQuestion()) {
            properties.questionTaStyle.set(QUESTION_STYLE_EDITED);
        } else {
            properties.questionTaStyle.set(Styles.QUESTION_STYLE_EMPTY);
        }
    }

    void pasteQuestionFromClipboard() {
        log.debug("pasteQuestionFromClipboard");
        var question = mediator.getTextFromClipboard();
        properties.questionTaText.setValue(question);
        mediator.setEditedQuestion(question);
    }

    private synchronized void createNewInteractionAndRequestAnswers(InteractionType interactionType) {
        log.debug("createNewInteractionAndRequestAnswers: interactionType={}", interactionType);
        var interactionId = mediator.createInteraction(interactionType);
        mediator.requestAnswer(interactionId, GCP);
        mediator.requestAnswer(interactionId, LONG);
        mediator.requestAnswer(interactionId, SHORT);
        mediator.requestAnswer(interactionId, GRAMMAR);
    }

    public static class Properties {
        public final StringProperty questionTaText = new SimpleStringProperty();
        public final StringProperty questionTaStyle = new SimpleStringProperty();
        public final BooleanProperty questionTaFocused = new SimpleBooleanProperty();
        public final BooleanProperty questionTaSelectAll = new SimpleBooleanProperty();
        public final BooleanProperty questionTaPositionCaretToEnd = new SimpleBooleanProperty();
    }
}

