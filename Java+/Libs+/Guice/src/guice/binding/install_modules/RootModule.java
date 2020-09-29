package guice.binding.install_modules;

import com.google.inject.AbstractModule;
import guice.binding.install_modules.name.NameModule;
import guice.binding.install_modules.title.TitleModule;

class RootModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new TitleModule());
        install(new NameModule());
        bind(RootService.class).to(RootServiceImpl.class);
    }
}
