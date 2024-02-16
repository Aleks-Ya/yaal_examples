package gptui;

import com.google.inject.AbstractModule;
import gptui.model.ModelModule;
import gptui.view.ViewModule;
import gptui.viewmodel.ViewModelModule;

public class RootModule extends AbstractModule {
    @Override
    protected void configure() {
        binder().requireExplicitBindings();
        install(new ModelModule());
        install(new ViewModelModule());
        install(new ViewModule());
    }
}
