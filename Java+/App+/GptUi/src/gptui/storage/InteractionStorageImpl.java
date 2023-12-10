package gptui.storage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;

@Singleton
class InteractionStorageImpl implements InteractionStorage {
    private final Map<InteractionId, Interaction> interactions = new LinkedHashMap<>();
    private final List<String> themes = new ArrayList<>();
    private final InteractionStorageFilesystem storageFilesystem;

    @Inject
    public InteractionStorageImpl(InteractionStorageFilesystem storageFilesystem) {
        this.storageFilesystem = storageFilesystem;
        storageFilesystem.readAllInteractions().forEach(interaction -> interactions.put(interaction.id(), interaction));
        readThemesFromInteractions();
    }

    private void readThemesFromInteractions() {
        themes.clear();
        themes.addAll(interactionsToThemes(readAllInteractions()));
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
        themes.remove(interaction.theme());
        themes.addFirst(interaction.theme());
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
        readThemesFromInteractions();
    }

    @Override
    public synchronized List<String> getThemes() {
        return themes;
    }

    private List<String> interactionsToThemes(List<Interaction> interactions) {
        return interactions.stream()
                .collect(groupingBy(Interaction::theme)).entrySet().stream().map(entry -> {
                    var theme = entry.getKey();
                    var latestInteraction = entry.getValue().stream()
                            .max(comparing(interaction -> interaction.id().id()))
                            .orElseThrow();
                    return entry(theme, latestInteraction);
                })
                .sorted(Comparator.<Map.Entry<String, Interaction>, Long>comparing(entry -> entry.getValue().id().id()).reversed())
                .map(Map.Entry::getKey)
                .toList();
    }

}
