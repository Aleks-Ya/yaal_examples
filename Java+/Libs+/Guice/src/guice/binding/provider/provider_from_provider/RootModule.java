package guice.binding.provider.provider_from_provider;

import com.google.inject.AbstractModule;
import guice.binding.provider.provider_from_provider.name.NameModule;
import guice.binding.provider.provider_from_provider.title.TitleModule;

class RootModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new TitleModule());
        install(new NameModule());
        bind(RootService.class).toProvider(RootProvider.class);
    }
}
