package gptui.viewmodel.uiapp;

import com.google.inject.AbstractModule;

public class GptUiApplicationVmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptUiApplicationVmController.class).to(GptUiApplicationVmImpl.class);
    }
}
