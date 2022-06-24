package guice.binding.install_modules;

import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Install modules (import other modules from a module).
 */
class InstallModulesTest {

    @Test
    void install() {
        var injector = Guice.createInjector(new RootModule());
        var service = injector.getInstance(RootService.class);
        assertThat(service.getPerson()).isEqualTo("Sr. Mark");
    }
}