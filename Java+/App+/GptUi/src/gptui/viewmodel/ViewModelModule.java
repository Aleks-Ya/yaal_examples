package gptui.viewmodel;

import com.google.inject.AbstractModule;
import gptui.viewmodel.answer.AnswerVmModule;
import gptui.viewmodel.history.HistoryVmModule;
import gptui.viewmodel.mediator.MediatorModule;
import gptui.viewmodel.question.QuestionVmModule;
import gptui.viewmodel.theme.ThemeVmModule;
import gptui.viewmodel.ui.GptUiVmModule;
import gptui.viewmodel.uiapp.GptUiApplicationVmModule;

public class ViewModelModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new QuestionVmModule());
        install(new ThemeVmModule());
        install(new HistoryVmModule());
        install(new AnswerVmModule());
        install(new GptUiVmModule());
        install(new GptUiApplicationVmModule());
        install(new MediatorModule());
    }
}
