package gptui.ui.model.question.gcp;

import com.google.inject.AbstractModule;

public class GcpModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GcpApi.class).to(GcpApiImpl.class);
    }
}
