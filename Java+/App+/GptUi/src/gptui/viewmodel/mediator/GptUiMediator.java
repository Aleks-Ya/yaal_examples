package gptui.viewmodel.mediator;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;

import java.util.List;

public interface GptUiMediator {
    void displayCurrentInteraction();

    Interaction getCurrentInteraction();

    List<Interaction> getFullHistory();

    void setTemperature(AnswerType answerType, Integer temperature);

    void chooseFirstInteractionAsCurrent();

    void chooseFirstThemeAsCurrent();
}
