package gptui.model.question;

import gptui.model.storage.AnswerType;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;

public interface QuestionModel {
    void sendQuestion(InteractionType interactionType);

    void requestAnswer(InteractionId interactionId, AnswerType answerType);
}