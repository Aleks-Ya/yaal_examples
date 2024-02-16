package gptui.model;

import com.google.inject.AbstractModule;
import gptui.model.file.FileModelModule;

public class ModelModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FileModelModule());
    }
}
