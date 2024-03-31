package gptui.viewmodel.mediator;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;

import java.util.Optional;

public interface GptUiMediator {
    void displayCurrentInteraction();

    Optional<Interaction> getCurrentInteractionOpt();

    void setTemperature(AnswerType answerType, Integer temperature);

    void chooseFirstInteractionAsCurrent();

    void chooseFirstThemeAsCurrent();
}
