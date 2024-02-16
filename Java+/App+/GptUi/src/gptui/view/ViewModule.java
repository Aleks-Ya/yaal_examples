package gptui.view;

import com.google.inject.AbstractModule;

public class ViewModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptUiController.class);
        bind(HistoryController.class);
        bind(ThemeController.class);
        bind(QuestionController.class);
        bind(AnswerController.class);
    }
}
