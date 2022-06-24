package guice.binding.override;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OverrideModuleTest {
    private static final List<Class<?>> invokedModules = new ArrayList<>();

    @Test
    void bind() {
        //PROD
        {
            invokedModules.clear();
            var injector = Guice.createInjector(new AppModule());
            assertThat(injector.getInstance(Source.class)).isInstanceOf(OracleSource.class);
            assertThat(injector.getInstance(Integer.class)).isEqualTo(42);
            assertThat(invokedModules).containsExactly(AppModule.class, ProductionSourceModule.class);
        }

        //TEST
        {
            invokedModules.clear();
            var injector = Guice.createInjector(Modules
                    .override(new AppModule())
                    .with(new TestSourceModule()));
            assertThat(injector.getInstance(Source.class)).isInstanceOf(H2Source.class);
            assertThat(injector.getInstance(Integer.class)).isEqualTo(42);
            assertThat(invokedModules).containsExactly(AppModule.class, ProductionSourceModule.class, TestSourceModule.class);
        }
    }

    interface Source {
    }

    private static class AppModule extends AbstractModule {
        @Override
        protected void configure() {
            invokedModules.add(getClass());
            bind(Integer.class).toInstance(42);
            install(new ProductionSourceModule());
        }
    }

    private static class ProductionSourceModule extends AbstractModule {
        @Override
        protected void configure() {
            invokedModules.add(getClass());
            bind(Source.class).to(OracleSource.class);
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