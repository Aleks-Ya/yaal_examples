package gptui.model.storage;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;

@Singleton
class StorageModelImpl implements StorageModel {
    private static final Logger log = LoggerFactory.getLogger(StorageModelImpl.class);
    private final Map<InteractionId, Interaction> interactions = new LinkedHashMap<>();
    private final List<String> themes = new ArrayList<>();
    private final StorageFilesystem storageFilesystem;
    private Map<ThemeId, Theme> themeCache = new HashMap<>();

    @Inject
    public StorageModelImpl(StorageFilesystem storageFilesystem) {
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
        var interactionId = new InteractionId(Instant.now().getEpochSecond());
        log.trace("newInteractionId: {}", interactionId);
        return interactionId;
    }

    @Override
    public synchronized void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        var interactionOpt = readInteraction(interactionId);
        Interaction interaction;
        if (interactionOpt.isEmpty()) {
            interaction = new Interaction(interactionId, null, null, null, null, null);
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
        if (interactionId == null) {
            return Optional.empty();
        }
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

    @Override
    public Theme addTheme(String theme) {
        var trimmed = theme.trim();
        var existingThemes = storageFilesystem.readThemes();
        var existingOpt = existingThemes.stream().filter(themeObj -> themeObj.title().equals(trimmed)).findFirst();
        var newThemeExists = existingOpt.isPresent();
        if (!newThemeExists) {
            var themes = new ArrayList<>(existingThemes);
            var maxId = existingThemes.stream().map(Theme::id).mapToLong(ThemeId::id).max().orElse(0L);
            var newId = ++maxId;
            var newTheme = new Theme(new ThemeId(newId), theme);
            themes.add(newTheme);
            updateThemeCaches(themes);
            return newTheme;
        } else {
            return existingOpt.get();
        }
    }

    private void updateThemeCaches(ArrayList<Theme> themes) {
        storageFilesystem.saveThemes(themes);
        themeCache = themes.stream().collect(Collectors.toMap(Theme::id, Function.identity()));
    }

    @Override
    public void saveTheme(Theme theme) {
        var existingThemes = storageFilesystem.readThemes();
        var existingOpt = existingThemes.stream().filter(themeObj -> themeObj.id().equals(theme.id())).findFirst();
        var newThemeExists = existingOpt.isPresent();
        if (!newThemeExists) {
            var themes = new ArrayList<>(existingThemes);
            themes.add(theme);
            storageFilesystem.saveThemes(themes);
            updateThemeCaches(themes);
        }
    }

    @Override
    public Theme getTheme(ThemeId themeId) {
        var theme = themeCache.get(themeId);
        if (theme == null) {
            throw new IllegalStateException("Theme was not found by id: " + themeId);
        }
        return theme;
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
