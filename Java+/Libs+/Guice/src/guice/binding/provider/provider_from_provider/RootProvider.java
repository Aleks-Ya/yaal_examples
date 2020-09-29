package guice.binding.provider.provider_from_provider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import guice.binding.provider.provider_from_provider.name.NameService;
import guice.binding.provider.provider_from_provider.title.TitleService;

class RootProvider implements Provider<RootServiceImpl> {
    private final Injector injector;

    @Inject
    RootProvider(Injector injector) {
        this.injector = injector;
    }

    @Override
    public RootServiceImpl get() {
        TitleService titleService = injector.getInstance(TitleService.class);
        NameService nameService = injector.getInstance(NameService.class);
        return new RootServiceImpl(titleService, nameService);
    }

}