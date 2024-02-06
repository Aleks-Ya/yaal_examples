package gptui.ui.model;

import com.google.inject.AbstractModule;

public class ModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileModel.class).to(FileModelImpl.class);
    }
}
