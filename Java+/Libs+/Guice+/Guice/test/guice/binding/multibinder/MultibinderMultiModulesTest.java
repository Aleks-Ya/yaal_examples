package guice.binding.multibinder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.multibindings.Multibinder;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class MultibinderMultiModulesTest {

    @Test
    public void bind() {
        var injector = Guice.createInjector(new RootModule());
        var processor = injector.getInstance(SourceProcessor.class);
        var source = processor.getSources();
        assertThat(source, hasSize(3));
    }

    private static class RootModule extends AbstractModule {
        @Override
        protected void configure() {
            var multibinder = Multibinder.newSetBinder(binder(), Source.class);
            multibinder.addBinding().to(HttpSource.class);
            install(new ModuleA());
            install(new ModuleB());
        }
    }

    private static class ModuleA extends AbstractModule {
        @Override
        protected void configure() {
            var multibinder = Multibinder.newSetBinder(binder(), Source.class);
            multibinder.addBinding().to(DatabaseSource.class);
        }
    }

    private static class ModuleB extends AbstractModule {
        @Override
        protected void configure() {
            var multibinder = Multibinder.newSetBinder(binder(), Source.class);
            multibinder.addBinding().toInstance(new FileSource());
        }
    }

    interface Source {
    }

    static class HttpSource implements Source {
    }

    static class DatabaseSource implements Source {
    }

    static class FileSource implements Source {
    }

    static class SourceProcessor {
        private final Set<Source> sources;

        @Inject
        SourceProcessor(Set<Source> sources) {
            this.sources = sources;
        }

        public Set<Source> getSources() {
            return sources;
        }
    }
}