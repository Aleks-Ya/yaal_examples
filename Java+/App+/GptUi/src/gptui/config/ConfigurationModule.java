package gptui.config;

import com.google.inject.AbstractModule;

public class ConfigurationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Configuration.class).to(ConfigurationImpl.class);
    }
}
