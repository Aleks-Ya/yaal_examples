package guice.binding.provider.provider_from_provider.name;

import com.google.inject.Provider;

class NameServiceProvider implements Provider<NameService> {

    @Override
    public NameService get() {
        return new NameServiceImpl("John");
    }

}