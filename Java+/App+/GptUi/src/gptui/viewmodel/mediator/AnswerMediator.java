package gptui.viewmodel.mediator;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;

import java.util.Optional;

public interface AnswerMediator {
    void selectNextHistoryItem();

    void putHtmlToClipboard(String html);

    Integer getTemperature(AnswerType answerType);

    void setTemperature(AnswerType answerType, Integer temperature);

    InteractionId getCurrentInteractionId();

    Optional<Interaction> getCurrentInteractionOpt();

    void requestAnswer(InteractionId interactionId, AnswerType answerType);
}
