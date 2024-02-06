package gptui.ui;

import com.google.inject.AbstractModule;
import gptui.config.ConfigurationModule;
import gptui.storage.StorageModule;
import gptui.ui.model.ModelModule;
import gptui.ui.model.question.gcp.GcpModule;
import gptui.ui.model.question.openai.OpenAiModule;
import gptui.ui.model.question.question.QuestionModule;

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
