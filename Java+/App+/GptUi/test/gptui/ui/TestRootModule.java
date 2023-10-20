package gptui.ui;

import com.google.common.jimfs.Jimfs;
import com.google.inject.AbstractModule;
import gptui.gpt.gcp.GcpApi;
import gptui.gpt.openai.GptApi;
import gptui.gpt.openai.MockGptApi;

import java.nio.file.FileSystem;

import static com.google.common.jimfs.Configuration.unix;

class TestRootModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(MockGptApi.class);
        bind(GcpApi.class).to(MockGptApi.class);
        bind(FileSystem.class).toInstance(Jimfs.newFileSystem(unix()));
    }
}
