package gptui.viewmodel.question;

import com.google.inject.AbstractModule;

public class QuestionVmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuestionVmMediator.class).to(QuestionVmImpl.class);
        bind(QuestionVmController.class).to(QuestionVmImpl.class);
    }
}
