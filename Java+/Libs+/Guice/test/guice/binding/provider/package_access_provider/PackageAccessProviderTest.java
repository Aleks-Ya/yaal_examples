package guice.binding.provider.package_access_provider;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.binding.provider.package_access_provider.package_provider.PackageProvider;
import guice.binding.provider.package_access_provider.package_provider.PackageService;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Instantiate classes having package access modifier.
 */
public class PackageAccessProviderTest {

    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(PackageService.class).toProvider(PackageProvider.class);
        }
    }

    @Test
    public void bind() {
        Injector injector = Guice.createInjector(new DemoModule());
        PackageService service = injector.getInstance(PackageService.class);
        String title = service.getTitle();
        assertThat(title, equalTo("the title"));
    }
}