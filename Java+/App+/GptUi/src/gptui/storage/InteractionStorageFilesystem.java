package gptui.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
class InteractionStorageFilesystem {
    private static final Logger log = LoggerFactory.getLogger(InteractionStorageFilesystem.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final FileSystem fileSystem;
    private final Path storageDir;

    @Inject
    public InteractionStorageFilesystem(FileSystem fileSystem) {
        try {
            this.fileSystem = fileSystem;
            storageDir = fileSystem.getPath(System.getProperty("user.home"), ".gpt/storage");
            if (Files.notExists(storageDir)) {
                Files.createDirectories(storageDir);
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
        return fileSystem.getPath(storageDir.toString(), interactionId.id() + ".json");
    }

    public synchronized List<Interaction> readAllInteractions() {
        try {
            var result = new ArrayList<Interaction>();
            try (var files = Files.list(storageDir)) {
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

}
