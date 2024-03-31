package gptui.viewmodel.mediator;

import com.google.inject.AbstractModule;

public class MediatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ViewModelMediatorImpl.class);
        bind(HistoryMediator.class).to(ViewModelMediatorImpl.class);
        bind(QuestionMediator.class).to(ViewModelMediatorImpl.class);
        bind(ThemeMediator.class).to(ViewModelMediatorImpl.class);
        bind(AnswerMediator.class).to(ViewModelMediatorImpl.class);
        bind(GptUiMediator.class).to(ViewModelMediatorImpl.class);
        bind(GptUiApplicationMediator.class).to(ViewModelMediatorImpl.class);
    }
}
