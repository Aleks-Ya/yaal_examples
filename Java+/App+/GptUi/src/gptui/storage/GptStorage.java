package gptui.storage;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GptStorage {
    private static final Gson gson = new Gson();
    private final File storageDir;

    public GptStorage() {
        storageDir = new File(System.getProperty("user.home"), ".gpt/storage");
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new IllegalStateException("Cannot create dir: " + storageDir.getAbsolutePath());
            }
        }
    }

    public void saveInteraction(Interaction interaction) {
        try {
            var file = new File(storageDir, Instant.now().getEpochSecond() + ".json");
            var json = gson.toJson(interaction);
            Files.writeString(file.toPath(), json);
            System.out.println("Interaction was saved to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Interaction> readAllInteractions() {
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
