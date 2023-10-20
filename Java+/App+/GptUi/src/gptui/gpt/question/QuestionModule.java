package gptui.gpt.question;

import com.google.inject.AbstractModule;
import gptui.gpt.QuestionApi;

public class QuestionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuestionApi.class).to(QuestionApiImpl.class);
    }
}
