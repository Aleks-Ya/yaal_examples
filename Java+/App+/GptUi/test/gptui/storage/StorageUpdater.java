package gptui.storage;

import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class StorageUpdater {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        var storage = new GptStorageFilesystem(FileSystems.getDefault());
        var temperature = BigDecimal.valueOf(0.7);
        new GptStorageImpl(storage).readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction
                            .withAnswer(GRAMMAR, answer -> answer.withTemperature(temperature))
                            .withAnswer(SHORT, answer -> answer.withTemperature(temperature))
                            .withAnswer(LONG, answer -> answer.withTemperature(temperature))
                            .withAnswer(GCP, answer -> answer.withTemperature(temperature));
                })
                .forEach(new GptStorageImpl(storage)::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }
}
