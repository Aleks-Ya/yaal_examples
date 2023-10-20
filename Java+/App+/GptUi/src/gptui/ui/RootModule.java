package gptui.ui;

import com.google.inject.AbstractModule;
import gptui.config.ConfigurationModule;
import gptui.gpt.openai.OpenAiModule;
import gptui.gpt.question.QuestionModule;
import gptui.storage.StorageModule;

public class RootModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigurationModule());
        install(new OpenAiModule());
        install(new QuestionModule());
        install(new StorageModule());
    }
}
