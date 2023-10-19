package gptui.ui;

import com.google.inject.AbstractModule;
import gptui.gpt.QuestionApi;
import gptui.gpt.QuestionApiImpl;
import gptui.gpt.openai.OpenAiModule;
import gptui.storage.StorageModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new OpenAiModule());
        install(new StorageModule());
        bind(QuestionApi.class).to(QuestionApiImpl.class);
    }
}
