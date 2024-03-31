package gptui.viewmodel.theme;

import com.google.inject.AbstractModule;

public class ThemeVmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThemeVmController.class).to(ThemeVmImpl.class);
        bind(ThemeVmMediator.class).to(ThemeVmImpl.class);
    }
}
