package gptui.model.question.question;

import com.google.inject.AbstractModule;
import gptui.model.question.QuestionModel;

public class QuestionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuestionModel.class).to(QuestionModelImpl.class);
        bind(FormatConverter.class);
        bind(PromptFactory.class);
        bind(SoundService.class);
    }
}
