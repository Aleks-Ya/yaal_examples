package gptui.viewmodel;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import gptui.model.storage.AnswerType;

public class ViewModelModule extends AbstractModule {
    public static final String GRAMMAR = "GrammarAnswerVM";
    public static final String SHORT = "ShortAnswerVM";
    public static final String LONG = "LongAnswerVM";
    public static final String GCP = "GcpAnswerVM";

    @Override
    protected void configure() {
        bind(GptUiApplicationVM.class);
        bind(GptUiVM.class);
        bind(HistoryVM.class);
        bind(ThemeVM.class);
        bind(QuestionVM.class);
        bind(ViewModelMediator.class);
        bind(AnswerVM.class).annotatedWith(Names.named(GRAMMAR)).toInstance(new AnswerVM(AnswerType.GRAMMAR));
        bind(AnswerVM.class).annotatedWith(Names.named(SHORT)).toInstance(new AnswerVM(AnswerType.SHORT));
        bind(AnswerVM.class).annotatedWith(Names.named(LONG)).toInstance(new AnswerVM(AnswerType.LONG));
        bind(AnswerVM.class).annotatedWith(Names.named(GCP)).toInstance(new AnswerVM(AnswerType.GCP));
    }
}
