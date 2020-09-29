package guice.binding.provider.provider_from_provider.name;

import com.google.inject.AbstractModule;

public class NameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NameService.class).toProvider(NameServiceProvider.class);
    }
}
