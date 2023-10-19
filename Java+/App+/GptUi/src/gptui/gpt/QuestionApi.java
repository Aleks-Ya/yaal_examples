package gptui.gpt;

import gptui.storage.AnswerType;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;

public interface QuestionApi {
    void sendQuestion(InteractionType interactionType);

    void requestAnswer(InteractionId interactionId, AnswerType answerType);
}
