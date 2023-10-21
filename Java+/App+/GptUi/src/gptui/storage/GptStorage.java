package gptui.storage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface GptStorage {
    InteractionId newInteractionId();

    void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update);

    void saveInteraction(Interaction interaction);

    Optional<Interaction> readInteraction(InteractionId interactionId);

    List<Interaction> readAllInteractions();

    void deleteInteraction(InteractionId interactionId);

    List<String> getThemes();
}
