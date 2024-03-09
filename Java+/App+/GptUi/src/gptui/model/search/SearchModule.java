package gptui.model.search;

import com.google.inject.AbstractModule;

public class SearchModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HistorySearchModel.class).to(HistorySearchModelImpl.class);
    }
}
