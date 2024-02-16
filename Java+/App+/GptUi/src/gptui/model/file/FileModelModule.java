package gptui.model.file;

import com.google.inject.AbstractModule;

public class FileModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileModel.class).to(FileModelImpl.class);
    }
}
