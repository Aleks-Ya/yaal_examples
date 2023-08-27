package gptui.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class GptStorage {
    private static final Logger log = LoggerFactory.getLogger(GptStorage.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File storageDir;

    public GptStorage() {
        storageDir = new File(System.getProperty("user.home"), ".gpt/storage");
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new IllegalStateException("Cannot create dir: " + storageDir.getAbsolutePath());
            }
        }
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
            if (isInteractionExists(interactionId)) {
                throw new IllegalStateException("Interaction already exists: " + interaction);
            }
        } else {
            interaction = interactionOpt.get();
        }
        var updatedInteraction = update.apply(interaction);
        saveInteraction(updatedInteraction);
    }

    public synchronized void saveInteraction(Interaction interaction) {
        try {
            var file = getInteractionFile(interaction.id());
            var json = gson.toJson(interaction);
            Files.writeString(file.toPath(), json);
            log.info("Interaction was saved to file: {}", file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getInteractionFile(InteractionId interactionId) {
        return new File(storageDir, interactionId.id() + ".json");
    }

    private boolean isInteractionExists(InteractionId interactionId) {
        return getInteractionFile(interactionId).exists();
    }

    public synchronized Optional<Interaction> readInteraction(InteractionId interactionId) {
        try {
            var file = getInteractionFile(interactionId);
            if (file.exists()) {
                var content = Files.readString(file.toPath());
                return Optional.of(gson.fromJson(content, Interaction.class));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Interaction> readAllInteractions() {
        try {
            var result = new ArrayList<Interaction>();
            var files = storageDir.listFiles();
            if (files != null) {
                for (var file : files) {
                    var interaction = gson.fromJson(new FileReader(file), Interaction.class);
                    result.add(interaction);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
