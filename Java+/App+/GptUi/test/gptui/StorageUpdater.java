package gptui;

import gptui.storage.GptStorageFilesystem;
import gptui.storage.GptStorageImpl;

import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageUpdater {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        var gptStorage = new GptStorageFilesystem(FileSystems.getDefault());
        new GptStorageImpl(gptStorage).readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction;
                })
                .forEach(new GptStorageImpl(gptStorage)::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }
}
