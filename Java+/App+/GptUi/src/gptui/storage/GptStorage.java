package gptui.storage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GptStorage {
    private final Map<InteractionId, Interaction> interactions = new HashMap<>();
    private final GptStorageFilesystem gptStorage = new GptStorageFilesystem();

    public GptStorage() {
        gptStorage.readAllInteractions().forEach(interaction -> interactions.put(interaction.id(), interaction));
    }

    public synchronized InteractionId newInteractionId() {
        return new InteractionId(Instant.now().getEpochSecond());
    }

    public synchronized void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        var interactionOpt = readInteraction(interactionId);
        Interaction interaction;
        if (interactionOpt.isEmpty()) {
            interaction = new Interaction(interactionId,
                    null, null, null, null,
                    null, null, null, null,
                    null, null);
            if (interactions.containsKey(interactionId)) {
                throw new IllegalStateException("Interaction already exists: " + interaction);
            }
        } else {
            interaction = interactionOpt.get();
        }
        var updatedInteraction = update.apply(interaction);
        saveInteraction(updatedInteraction);
    }

    public synchronized void saveInteraction(Interaction interaction) {
        interactions.put(interaction.id(), interaction);
        gptStorage.saveInteraction(interaction);
    }

    public synchronized Optional<Interaction> readInteraction(InteractionId interactionId) {
        return Optional.ofNullable(interactions.get(interactionId));
    }

    public synchronized List<Interaction> readAllInteractions() {
        return new ArrayList<>(interactions.values());
    }

}