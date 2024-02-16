package gptui.model.storage;

import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;

public class StorageUpdater {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        var storage = new InteractionStorageFilesystem(FileSystems.getDefault());
        var temperature = 70;
        new StorageModelImpl(storage).readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction
                            .withAnswer(GRAMMAR, answer -> answer.withTemperature(temperature))
                            .withAnswer(SHORT, answer -> answer.withTemperature(temperature))
                            .withAnswer(LONG, answer -> answer.withTemperature(temperature))
                            .withAnswer(GCP, answer -> answer.withTemperature(temperature));
                })
                .forEach(new StorageModelImpl(storage)::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }
}
