package gptui.model.clipboard;

import com.google.inject.AbstractModule;

public class ClipboardModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClipboardModel.class).to(ClipboardModelImpl.class);
    }
}
