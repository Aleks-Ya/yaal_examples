package gptui.ui;

import com.google.inject.AbstractModule;
import gptui.gpt.GptApi;
import gptui.gpt.MockGptApi;
import gptui.gpt.QuestionApi;
import gptui.storage.GptStorage;

class TestGuiceModule extends AbstractModule {
    private final Model model;
    private final MockGptApi gptApi;
    private final GptStorage storage;
    private final QuestionApi questionApi;

    public TestGuiceModule(Model model, MockGptApi gptApi, GptStorage storage, QuestionApi questionApi) {
        this.model = model;
        this.gptApi = gptApi;
        this.storage = storage;
        this.questionApi = questionApi;
    }

    @Override
    protected void configure() {
        bind(Model.class).toInstance(model);
        bind(GptApi.class).toInstance(gptApi);
        bind(QuestionApi.class).toInstance(questionApi);
        bind(GptStorage.class).toInstance(storage);
    }
}
