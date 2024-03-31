package gptui.viewmodel.mediator;

import com.google.inject.AbstractModule;

public class MediatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MediatorImpl.class);
        bind(HistoryMediator.class).to(MediatorImpl.class);
        bind(QuestionMediator.class).to(MediatorImpl.class);
        bind(ThemeMediator.class).to(MediatorImpl.class);
        bind(AnswerMediator.class).to(MediatorImpl.class);
        bind(GptUiMediator.class).to(MediatorImpl.class);
        bind(GptUiApplicationMediator.class).to(MediatorImpl.class);
    }
}
