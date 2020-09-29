package guice.binding.install_modules.name;

import com.google.inject.AbstractModule;

public class NameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NameService.class).to(NameServiceImpl.class);
    }
}
