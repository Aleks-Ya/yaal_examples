package gptui.model.question;

import gptui.model.storage.AnswerType;
import gptui.model.storage.InteractionId;

public interface QuestionModel {
    void requestAnswer(InteractionId interactionId, AnswerType answerType, Runnable callback);
}
