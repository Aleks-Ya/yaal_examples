package gptui.ui.model.question.question;

import com.google.inject.AbstractModule;
import gptui.ui.model.question.QuestionModel;

public class QuestionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuestionModel.class).to(QuestionModelImpl.class);
    }
}
