package gptui.viewmodel.question;

import com.google.inject.Singleton;
import gptui.Mdc;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionType;
import gptui.viewmodel.mediator.QuestionMediator;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static gptui.model.storage.InteractionType.DEFINITION;
import static gptui.model.storage.InteractionType.FACT;
import static gptui.model.storage.InteractionType.QUESTION;
import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EDITED;

@Singleton
class QuestionVmImpl implements QuestionVmController, QuestionVmMediator {
    private static final Logger log = LoggerFactory.getLogger(QuestionVmImpl.class);
    private final QuestionVmProperties properties = new QuestionVmProperties();
    @Inject
    private QuestionMediator mediator;

    @Override
    public void displayCurrentInteraction() {
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

    @Override
    public void focusOnQuestionAndSelect() {
        log.debug("focusOnQuestion");
        properties.questionTaFocused.setValue(false);
        properties.questionTaFocused.setValue(true);
        properties.questionTaSelectAll.setValue(false);
        properties.questionTaSelectAll.setValue(true);
    }

    @Override
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

    @Override
    public void onSendQuestionClick() {
        log.debug("onSendQuestionClick");
        createNewInteractionAndRequestAnswers(QUESTION);
    }

    @Override
    public void onSendDefinitionClick() {
        log.debug("onSendDefinitionClick");
        createNewInteractionAndRequestAnswers(DEFINITION);
    }

    @Override
    public void onSendGrammarClick() {
        log.debug("onSendGrammarClick");
        createNewInteractionAndRequestAnswers(InteractionType.GRAMMAR);
    }

    @Override
    public void onSendFactClick() {
        log.debug("onSendFactClick");
        createNewInteractionAndRequestAnswers(FACT);
    }

    @Override
    public void onKeyTypedQuestionTextArea() {
        log.trace("onKeyTypedQuestionTextArea");
        mediator.setEditedQuestion(properties.questionTaText.getValue());
        updateQuestionTextAreaBackgroundColor();
    }

    @Override
    public QuestionVmProperties properties() {
        return properties;
    }

    private void updateQuestionTextAreaBackgroundColor() {
        if (mediator.isEnteringNewQuestion()) {
            properties.questionTaStyle.set(QUESTION_STYLE_EDITED);
        } else {
            properties.questionTaStyle.set(QuestionStyle.QUESTION_STYLE_EMPTY);
        }
    }

    @Override
    public void pasteQuestionFromClipboard() {
        log.debug("pasteQuestionFromClipboard");
        var question = mediator.getTextFromClipboard();
        properties.questionTaText.setValue(question);
        mediator.setEditedQuestion(question);
    }

    public synchronized void createNewInteractionAndRequestAnswers(InteractionType interactionType) {
        log.debug("createNewInteractionAndRequestAnswers: interactionType={}", interactionType);
        var interactionId = mediator.createInteraction(interactionType);
        mediator.requestAnswer(interactionId, GCP);
        mediator.requestAnswer(interactionId, LONG);
        mediator.requestAnswer(interactionId, SHORT);
        mediator.requestAnswer(interactionId, GRAMMAR);
    }
}

