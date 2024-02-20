package gptui.model.state;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;

import java.util.List;
import java.util.Optional;

public interface StateModel {
    boolean isEnteringNewQuestion();

    List<Interaction> getFullHistory();

    List<Interaction> getFilteredHistory();

    InteractionId getCurrentInteractionId();

    Interaction getCurrentInteraction();

    Optional<Interaction> getCurrentInteractionOpt();

    void setCurrentInteractionId(InteractionId currentInteractionId);

    void deleteCurrentInteraction();

    List<String> getThemes();

    String getCurrentTheme();

    void setCurrentTheme(String currentTheme);

    void setFirstThemeAsCurrent();

    String getEditedQuestion();

    void setEditedQuestion(String question);

    Integer getTemperature(AnswerType answerType);

    void setTemperature(AnswerType answerType, Integer temperature);

    Boolean isHistoryFilteringEnabled();

    void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled);

    void chooseFirstInteractionAsCurrent();
}
