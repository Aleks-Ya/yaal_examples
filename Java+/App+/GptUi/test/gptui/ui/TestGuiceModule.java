package gptui.ui;

import com.google.common.jimfs.Jimfs;
import com.google.inject.AbstractModule;
import gptui.gpt.GptApi;
import gptui.gpt.MockGptApi;

import java.nio.file.FileSystem;

import static com.google.common.jimfs.Configuration.unix;

class TestGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(MockGptApi.class);
        bind(FileSystem.class).toInstance(Jimfs.newFileSystem(unix()));
    }
}
