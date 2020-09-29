package guice.binding.provider.provider_from_provider.title;

import com.google.inject.Provider;

class TitleServiceProvider implements Provider<TitleService> {

    @Override
    public TitleServiceImpl get() {
        return new TitleServiceImpl("Mr.");
    }

}