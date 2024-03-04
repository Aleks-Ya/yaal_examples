package gptui.model.storage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface StorageModel {
    InteractionId newInteractionId();

    void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update);

    void saveInteraction(Interaction interaction);

    Optional<Interaction> readInteraction(InteractionId interactionId);

    List<Interaction> readAllInteractions();

    void deleteInteraction(InteractionId interactionId);

    List<String> getThemes();

    Theme addTheme(String theme);

    void saveTheme(Theme theme);

    Theme getTheme(ThemeId themeId);
}
