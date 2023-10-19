package gptui.storage;

import com.google.inject.AbstractModule;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class StorageModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileSystem.class).toInstance(FileSystems.getDefault());
        bind(GptStorage.class).to(GptStorageImpl.class);
    }
}
