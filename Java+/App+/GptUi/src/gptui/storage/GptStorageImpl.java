package gptui.storage;

import javax.inject.Singleton;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class GptStorageImpl implements GptStorage {
    private final Map<InteractionId, Interaction> interactions = new LinkedHashMap<>();
    private final GptStorageFilesystem storageFilesystem;

    public GptStorageImpl(GptStorageFilesystem storageFilesystem) {
        this.storageFilesystem = storageFilesystem;
        storageFilesystem.readAllInteractions().forEach(interaction -> interactions.put(interaction.id(), interaction));
    }

    @Override
    public synchronized InteractionId newInteractionId() {
        return new InteractionId(Instant.now().getEpochSecond());
    }

    @Override
    public synchronized void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        var interactionOpt = readInteraction(interactionId);
        Interaction interaction;
        if (interactionOpt.isEmpty()) {
            interaction = new Interaction(interactionId, null, null, null, null);
            if (interactions.containsKey(interactionId)) {
                throw new IllegalStateException("Interaction already exists: " + interaction);
            }
        } else {
            interaction = interactionOpt.get();
        }
        var updatedInteraction = update.apply(interaction);
        saveInteraction(updatedInteraction);
    }

    @Override
    public synchronized void saveInteraction(Interaction interaction) {
        interactions.put(interaction.id(), interaction);
        storageFilesystem.saveInteraction(interaction);
    }

    @Override
    public synchronized Optional<Interaction> readInteraction(InteractionId interactionId) {
        return Optional.ofNullable(interactions.get(interactionId));
    }

    @Override
    public synchronized List<Interaction> readAllInteractions() {
        return interactions.values().stream()
                .sorted((i1, i2) -> i2.id().id().compareTo(i1.id().id()))
                .toList();
    }

    @Override
    public synchronized void deleteInteraction(InteractionId interactionId) {
        storageFilesystem.deleteInteraction(interactionId);
        interactions.remove(interactionId);
    }

}
