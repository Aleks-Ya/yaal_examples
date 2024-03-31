package gptui.viewmodel.history;

import com.google.inject.AbstractModule;

public class HistoryVmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HistoryVmController.class).to(HistoryVmImpl.class);
        bind(HistoryVmMediator.class).to(HistoryVmImpl.class);
    }
}
