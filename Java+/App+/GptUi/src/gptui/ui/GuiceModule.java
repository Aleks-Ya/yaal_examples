package gptui.ui;

import com.google.inject.AbstractModule;
import gptui.gpt.GptApi;
import gptui.gpt.GptApiImpl;
import gptui.storage.GptStorage;
import gptui.storage.GptStorageFilesystem;
import gptui.storage.GptStorageImpl;

import java.nio.file.FileSystems;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(GptApiImpl.class);
        bind(GptStorage.class).toInstance(new GptStorageImpl(new GptStorageFilesystem(FileSystems.getDefault())));
    }
}
