package gptui.model.state;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;
import gptui.model.storage.Theme;
import gptui.model.storage.ThemeId;

import java.util.List;
import java.util.Optional;

public interface StateModel {
    boolean isEnteringNewQuestion();

    List<Interaction> getFullHistory();

    List<Interaction> getFilteredHistory();

    InteractionId getCurrentInteractionId();

    Optional<Interaction> getCurrentInteractionOpt();

    void setCurrentInteractionId(InteractionId currentInteractionId);

    InteractionId createInteraction(InteractionType interactionType);

    void deleteCurrentInteraction();

    List<Theme> getThemes();

    Theme addTheme(String theme);

    Theme getTheme(ThemeId themeId);

    Long getInteractionCountInTheme(String theme);

    Theme getCurrentTheme();

    void setCurrentTheme(Theme currentTheme);

    void setFirstThemeAsCurrent();

    String getEditedQuestion();

    void setEditedQuestion(String question);

    Integer getTemperature(AnswerType answerType);

    void setTemperature(AnswerType answerType, Integer temperature);

    Boolean isHistoryFilteringEnabled();

    void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled);

    void chooseFirstInteractionAsCurrent();
}
