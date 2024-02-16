package gptui.model.event;

import com.google.inject.AbstractModule;

public class EventModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventModel.class);
    }
}
