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
    }

    private static void convertInteractions(StorageModel storageModel) {
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
}
