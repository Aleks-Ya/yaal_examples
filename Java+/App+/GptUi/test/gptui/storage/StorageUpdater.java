package gptui.storage;

import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerType.GCP;

public class StorageUpdater {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        var storage = new GptStorageFilesystem(FileSystems.getDefault());
        new GptStorageImpl(storage).readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction.withAnswer(GCP, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW));
                })
                .forEach(new GptStorageImpl(storage)::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }
}
