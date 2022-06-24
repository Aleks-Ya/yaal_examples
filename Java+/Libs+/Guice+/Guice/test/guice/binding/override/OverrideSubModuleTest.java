package guice.binding.override;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OverrideSubModuleTest {
    private static final List<Class<?>> invokedModules = new ArrayList<>();

    @Test
    void bind() {
        var testInjector = Guice.createInjector(new TestAppModule());
        var testSource = testInjector.getInstance(Source.class);
        assertThat(testSource).isInstanceOf(H2Source.class);
        var testInteger = testInjector.getInstance(Integer.class);
        assertThat(testInteger).isEqualTo(42);
        assertThat(invokedModules).containsExactly(ProdAppModule.class, TestAppModule.class, TestSourceModule.class);
    }

    interface Source {
    }

    private static class ProdAppModule extends AbstractModule {
        @Override
        protected void configure() {
            invokedModules.add(ProdAppModule.class);
            bind(Integer.class).toInstance(42);
            installSourceModule();
        }

        protected void installSourceModule() {
            install(new ProductionSourceModule());
        }
    }

    private static class TestAppModule extends ProdAppModule {
        @Override
        protected void installSourceModule() {
            invokedModules.add(TestAppModule.class);
            install(new TestSourceModule());
        }
    }

    private static class ProductionSourceModule extends AbstractModule {
        @Override
        protected void configure() {
            invokedModules.add(getClass());
            bind(Source.class).to(OracleSource.class);
            throw new AssertionError("Should not instantiate ProductionSubModule");
        }
    }

    private static class TestSourceModule extends AbstractModule {
        @Override
        protected void configure() {
            invokedModules.add(getClass());
            bind(Source.class).to(H2Source.class);
        }
    }

    static class OracleSource implements Source {
    }

    static class H2Source implements Source {
    }
}