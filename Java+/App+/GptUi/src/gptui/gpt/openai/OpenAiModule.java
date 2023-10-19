package gptui.gpt.openai;

import com.google.inject.AbstractModule;
import gptui.gpt.GptApi;

public class OpenAiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(GptApiImpl.class);
    }
}
