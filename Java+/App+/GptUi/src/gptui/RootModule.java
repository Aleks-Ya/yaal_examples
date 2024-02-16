package gptui;

import com.google.inject.AbstractModule;
import gptui.model.ModelModule;
import gptui.model.config.ConfigurationModule;
import gptui.model.question.gcp.GcpModule;
import gptui.model.question.openai.OpenAiModule;
import gptui.model.question.question.QuestionModule;
import gptui.model.storage.StorageModule;

public class RootModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigurationModule());
        install(new OpenAiModule());
        install(new GcpModule());
        install(new QuestionModule());
        install(new StorageModule());
        install(new ModelModule());
    }
}
