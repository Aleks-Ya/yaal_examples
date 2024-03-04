package gptui.model.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Singleton
class StorageFilesystem {
    private static final Logger log = LoggerFactory.getLogger(StorageFilesystem.class);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(InteractionId.class, new InteractionIdSerDe())
            .registerTypeAdapter(ThemeId.class, new ThemeIdSerDe())
            .setPrettyPrinting()
            .create();
    private final FileSystem fileSystem;
    private final Path interactionsDir;
    private final Path themesFile;

    @Inject
    public StorageFilesystem(FileSystem fileSystem) {
        try {
            this.fileSystem = fileSystem;
            var storageDir = fileSystem.getPath(System.getProperty("user.home"), ".gpt/storage");
            if (Files.notExists(storageDir)) {
                Files.createDirectories(storageDir);
            }
            interactionsDir = storageDir.resolve("interactions");
            if (Files.notExists(interactionsDir)) {
                Files.createDirectories(interactionsDir);
            }
            themesFile = storageDir.resolve("themes.json");
            if (Files.notExists(themesFile)) {
                saveThemes(List.of());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void saveInteraction(Interaction interaction) {
        try {
            var file = getInteractionFile(interaction.id());
            var json = gson.toJson(interaction);
            Files.writeString(file, json);
            log.info("Interaction was saved to file: {}", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getInteractionFile(InteractionId interactionId) {
        return fileSystem.getPath(interactionsDir.toString(), interactionId.id() + ".json");
    }

    public synchronized List<Interaction> readAllInteractions() {
        try {
            var result = new ArrayList<Interaction>();
            try (var files = Files.list(interactionsDir)) {
                for (var file : files.toList()) {
                    var interaction = gson.fromJson(Files.readString(file), Interaction.class);
                    result.add(interaction);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void deleteInteraction(InteractionId interactionId) {
        try {
            Files.delete(getInteractionFile(interactionId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Theme> readThemes() {
        try {
            var json = Files.readString(themesFile);
            var type = new TypeToken<List<Theme>>() {
            }.getType();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void saveThemes(List<Theme> themes) {
        try {
            var json = gson.toJson(themes);
            Files.writeString(themesFile, json);
            log.info("Themes was saved to file: count={}, file={}", themes.size(), themesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
