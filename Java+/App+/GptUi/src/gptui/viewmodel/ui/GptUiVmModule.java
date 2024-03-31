package gptui.viewmodel.ui;

import com.google.inject.AbstractModule;

public class GptUiVmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptUiVmController.class).to(GptUiVmImpl.class);
    }
}
