package guice.binding.install_modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Install modules (import other modules from a module).
 */
public class InstallModulesTest {

    @Test
    public void install() {
        var injector = Guice.createInjector(new RootModule());
        var service = injector.getInstance(RootService.class);
        assertThat(service.getPerson(), equalTo("Sr. Mark"));
    }
}