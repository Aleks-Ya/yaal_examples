package gptui;

import gptui.storage.GptStorageFilesystem;
import gptui.storage.GptStorageImpl;

import java.nio.file.FileSystems;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageUpdater {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        var storage = new GptStorageFilesystem(FileSystems.getDefault());
        //noinspection SimplifyStreamApiCallChains
        new GptStorageImpl(storage).readAllInteractions().stream()
                .map(interaction -> {
                    counter.incrementAndGet();
                    return interaction;
                })
                .forEach(new GptStorageImpl(storage)::saveInteraction);
        System.out.println("Counter: " + counter.get());
    }
}
