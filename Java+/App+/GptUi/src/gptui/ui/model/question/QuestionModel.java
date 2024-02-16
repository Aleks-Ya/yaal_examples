package gptui.ui.model.question;

import gptui.storage.AnswerType;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;

public interface QuestionModel {
    void sendQuestion(InteractionType interactionType);

    void requestAnswer(InteractionId interactionId, AnswerType answerType);
}
