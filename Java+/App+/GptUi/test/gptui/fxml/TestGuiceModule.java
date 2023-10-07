package gptui.fxml;

import com.google.inject.AbstractModule;
import gptui.MockGptApi;
import gptui.gpt.GptApi;
import gptui.storage.GptStorage;

class TestGuiceModule extends AbstractModule {
    private final MockGptApi gptApi;
    private final GptStorage storage;

    public TestGuiceModule(MockGptApi gptApi, GptStorage storage) {
        this.gptApi = gptApi;
        this.storage = storage;
    }

    @Override
    protected void configure() {
        bind(GptApi.class).toInstance(gptApi);
        bind(GptStorage.class).toInstance(storage);
    }
}
