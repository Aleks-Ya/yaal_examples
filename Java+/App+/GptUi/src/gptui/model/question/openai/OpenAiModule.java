package gptui.model.question.openai;

import com.google.inject.AbstractModule;

public class OpenAiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(GptApiImpl.class);
    }
}
