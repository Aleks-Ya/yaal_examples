package gptui.fxml;

import com.google.inject.AbstractModule;
import gptui.MockGptApi;
import gptui.gpt.GptApi;
import gptui.storage.GptStorage;

class TestGuiceModule extends AbstractModule {
    private final Model model;
    private final MockGptApi gptApi;
    private final GptStorage storage;

    public TestGuiceModule(Model model, MockGptApi gptApi, GptStorage storage) {
        this.model = model;
        this.gptApi = gptApi;
        this.storage = storage;
    }

    @Override
    protected void configure() {
        bind(Model.class).toInstance(model);
        bind(GptApi.class).toInstance(gptApi);
        bind(GptStorage.class).toInstance(storage);
    }
}
