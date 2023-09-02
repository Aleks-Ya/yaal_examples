package gptui.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class GptStorageFilesystem {
    private static final Logger log = LoggerFactory.getLogger(GptStorageFilesystem.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File storageDir;

    public GptStorageFilesystem() {
        storageDir = new File(System.getProperty("user.home"), ".gpt/storage");
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new IllegalStateException("Cannot create dir: " + storageDir.getAbsolutePath());
            }
        }
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
