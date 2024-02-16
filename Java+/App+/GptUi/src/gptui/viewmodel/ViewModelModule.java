package gptui.viewmodel;

import com.google.inject.AbstractModule;

public class ViewModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptUiApplicationVM.class);
        bind(AnswersVM.class);
        bind(HistoryVM.class);
        bind(ThemeVM.class);
        bind(QuestionVM.class);
        bind(AnswerVM.class);
    }
}
