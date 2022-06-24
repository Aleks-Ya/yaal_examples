package guice.binding.provider.provider_from_provider;

import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use other providers from a provider.
 */
class ProviderFromProviderTest {

    @Test
    void bind() {
        var injector = Guice.createInjector(new RootModule());
        var service = injector.getInstance(RootService.class);
        assertThat(service.getPerson()).isEqualTo("Mr. John");
    }
}