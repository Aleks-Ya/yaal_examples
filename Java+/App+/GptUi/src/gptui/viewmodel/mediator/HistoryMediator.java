package gptui.viewmodel.mediator;

import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.Theme;
import gptui.model.storage.ThemeId;

import java.util.List;
import java.util.Optional;

public interface HistoryMediator {
    void displayCurrentInteraction();

    Interaction getCurrentInteraction();

    Optional<Interaction> getCurrentInteractionOpt();

    void setCurrentInteractionId(InteractionId currentInteractionId);

    void deleteCurrentInteraction();

    List<Interaction> getFullHistory();

    List<Interaction> getFilteredHistory();

    Theme getCurrentTheme();

    Theme getTheme(ThemeId themeId);
}
