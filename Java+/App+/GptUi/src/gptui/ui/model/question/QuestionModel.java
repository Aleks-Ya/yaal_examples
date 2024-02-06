package gptui.ui.model.question;

import gptui.storage.AnswerType;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;
import gptui.ui.model.Temperatures;

public interface QuestionModel {
    void sendQuestion(InteractionType interactionType, Temperatures temperatures);

    void requestAnswer(InteractionId interactionId, AnswerType answerType);
}
