package guice.binding.install_modules.title;

import com.google.inject.AbstractModule;

public class TitleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TitleService.class).to(TitleServiceImpl.class);
    }
}
