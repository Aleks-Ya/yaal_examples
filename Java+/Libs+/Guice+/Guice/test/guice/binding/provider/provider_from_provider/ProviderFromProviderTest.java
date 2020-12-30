package guice.binding.provider.provider_from_provider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Use other providers from a provider.
 */
public class ProviderFromProviderTest {

    @Test
    public void bind() {
        var injector = Guice.createInjector(new RootModule());
        var service = injector.getInstance(RootService.class);
        assertThat(service.getPerson(), equalTo("Mr. John"));
    }
}