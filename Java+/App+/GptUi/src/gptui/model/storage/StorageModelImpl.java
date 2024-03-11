package gptui.model.storage;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Singleton
class StorageModelImpl implements StorageModel {
    private static final Logger log = LoggerFactory.getLogger(StorageModelImpl.class);
    private final Map<InteractionId, Interaction> interactions = new LinkedHashMap<>();
    private final List<Theme> themeList = new ArrayList<>();
    private final StorageFilesystem storageFilesystem;
    private final Map<ThemeId, Theme> themeMap = new HashMap<>();

    @Inject
    public StorageModelImpl(StorageFilesystem storageFilesystem) {
        this.storageFilesystem = storageFilesystem;
        storageFilesystem.readAllInteractions().forEach(interaction -> interactions.put(interaction.id(), interaction));
        readThemesFromInteractions();
    }

    private void readThemesFromInteractions() {
        themeMap.clear();
        storageFilesystem.readThemes().stream()
                .filter(theme -> !themeList.contains(theme))
                .forEach(themeList::add);
        storageFilesystem.readThemes().forEach(theme -> themeMap.put(theme.id(), theme));
        var sortedThemeIds = readAllInteractions().stream().map(Interaction::themeId).distinct().toList();
        sortedThemeIds.forEach(themeId -> {
            var theme = themeMap.get(themeId);
            themeList.remove(theme);
            themeList.addLast(theme);
        });
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
        var theme = getTheme(interaction.themeId());
        themeList.remove(theme);
        themeList.addFirst(theme);
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
    }

    @Override
    public List<Theme> getThemes() {
        return themeList;
    }

    @Override
    public synchronized Theme addTheme(String theme) {
        log.trace("Adding theme: {}", theme);
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
            storageFilesystem.saveThemes(themes);
            updateThemeCaches(themes);
            log.trace("Theme was added: {}", newTheme);
            return newTheme;
        } else {
            log.trace("Skip adding existing Theme: {}", existingOpt.get());
            return existingOpt.get();
        }
    }

    private void updateThemeCaches(List<Theme> themes) {
        themeList.clear();
        themeList.addAll(themes);
        themeMap.clear();
        themeMap.putAll(themes.stream().collect(toMap(Theme::id, identity())));
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
        var theme = themeMap.get(themeId);
        if (theme == null) {
            throw new IllegalStateException("Theme was not found by id: " + themeId);
        }
        return theme;
    }
}
