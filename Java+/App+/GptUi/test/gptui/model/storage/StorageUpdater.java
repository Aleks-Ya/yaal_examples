package gptui.model.storage;

import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;

public class StorageUpdater {
    public static void main(String[] args) {
        var storage = new StorageFilesystem(FileSystems.getDefault());
        var storageModel = new StorageModelImpl(storage);
        convertInteractions(storageModel);
        convertThemes(storageModel, storage);
        setThemeIds(storage, storageModel);
    }

    private static void convertInteractions(StorageModelImpl storageModel) {
        var counter = new AtomicInteger();
        var temperature = 70;
        storageModel.readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction
                            .withAnswer(GRAMMAR, answer -> answer.withTemperature(temperature))
                            .withAnswer(SHORT, answer -> answer.withTemperature(temperature))
                            .withAnswer(LONG, answer -> answer.withTemperature(temperature))
                            .withAnswer(GCP, answer -> answer.withTemperature(temperature));
                })
                .forEach(storageModel::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }

    private static void convertThemes(StorageModelImpl storageModel, StorageFilesystem storage) {
        var themes = storageModel.getThemes();
        themes.forEach(storageModel::addTheme);
        var actThemes = storage.readThemes();
        System.out.println(actThemes);
    }

    private static void setThemeIds(StorageFilesystem storage, StorageModelImpl storageModel) {
        var themes = storage.readThemes();
        var allInteractions = storageModel.readAllInteractions();
        allInteractions.forEach(interaction -> {
            var themeTitle = interaction.theme().trim();
            var themeOpt = themes.stream().filter(themeObj -> themeObj.title().equals(themeTitle)).findFirst();
            if (themeOpt.isPresent()) {
                var updated = interaction.withThemeId(themeOpt.get().id());
                storage.saveInteraction(updated);
            } else {
                System.out.println("Theme not found: '" + themeTitle + "'");
            }
        });
    }
}
