package gptui.viewmodel.mediator;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;

import java.util.Optional;

public interface QuestionMediator {
    InteractionId getCurrentInteractionId();

    Optional<Interaction> getCurrentInteractionOpt();

    void setEditedQuestion(String question);

    Boolean isEnteringNewQuestion();

    void requestAnswer(InteractionId interactionId, AnswerType answerType);

    InteractionId createInteraction(InteractionType interactionType);

    String getTextFromClipboard();
}
