package guice.binding.provider.provider_from_provider.title;

import com.google.inject.AbstractModule;

public class TitleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TitleService.class).toProvider(TitleServiceProvider.class);
    }
}
