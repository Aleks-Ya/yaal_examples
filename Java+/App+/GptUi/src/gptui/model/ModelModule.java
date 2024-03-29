package gptui.model;

import com.google.inject.AbstractModule;
import gptui.model.clipboard.ClipboardModelModule;
import gptui.model.config.ConfigurationModule;
import gptui.model.file.FileModelModule;
import gptui.model.question.gcp.GcpModule;
import gptui.model.question.openai.OpenAiModule;
import gptui.model.question.question.QuestionModule;
import gptui.model.search.SearchModule;
import gptui.model.state.StateModelModule;
import gptui.model.storage.StorageModule;

public class ModelModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FileModelModule());
        install(new ConfigurationModule());
        install(new OpenAiModule());
        install(new GcpModule());
        install(new QuestionModule());
        install(new StorageModule());
        install(new StateModelModule());
        install(new ClipboardModelModule());
        install(new SearchModule());
    }
}
