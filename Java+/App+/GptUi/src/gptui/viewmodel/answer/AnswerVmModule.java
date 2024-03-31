package gptui.viewmodel.answer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import gptui.model.storage.AnswerType;

public class AnswerVmModule extends AbstractModule {
    public static final String GRAMMAR = "GrammarAnswerVM";
    public static final String SHORT = "ShortAnswerVM";
    public static final String LONG = "LongAnswerVM";
    public static final String GCP = "GcpAnswerVM";

    @Override
    protected void configure() {
        var grammarAnswer = new AnswerVmImpl(AnswerType.GRAMMAR);
        var shortAnswer = new AnswerVmImpl(AnswerType.SHORT);
        var longAnswer = new AnswerVmImpl(AnswerType.LONG);
        var gcpAnswer = new AnswerVmImpl(AnswerType.GCP);

        bind(AnswerVmController.class).annotatedWith(Names.named(GRAMMAR)).toInstance(grammarAnswer);
        bind(AnswerVmController.class).annotatedWith(Names.named(SHORT)).toInstance(shortAnswer);
        bind(AnswerVmController.class).annotatedWith(Names.named(LONG)).toInstance(longAnswer);
        bind(AnswerVmController.class).annotatedWith(Names.named(GCP)).toInstance(gcpAnswer);

        bind(AnswerVmMediator.class).annotatedWith(Names.named(GRAMMAR)).toInstance(grammarAnswer);
        bind(AnswerVmMediator.class).annotatedWith(Names.named(SHORT)).toInstance(shortAnswer);
        bind(AnswerVmMediator.class).annotatedWith(Names.named(LONG)).toInstance(longAnswer);
        bind(AnswerVmMediator.class).annotatedWith(Names.named(GCP)).toInstance(gcpAnswer);
    }
}
