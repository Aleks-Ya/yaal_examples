package guice.binding.multibinder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.multibindings.Multibinder;
import org.junit.jupiter.api.Test;

import javax.inject.Named;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MultibinderNamedTest {

    @Test
    void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var processor = injector.getInstance(SourceProcessor.class);
        var source = processor.getSources();
        assertThat(source).hasSize(3);
    }

    interface Source {
    }

    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            var multibinder = Multibinder.newSetBinder(binder(), Source.class);
            multibinder.addBinding().to(HttpSource.class);
            multibinder.addBinding().to(DatabaseSource.class);
            multibinder.addBinding().toInstance(new FileSource());
        }
    }

    @Named("http")
    static class HttpSource implements Source {
    }

    @Named("database")
    static class DatabaseSource implements Source {
    }

    @Named("file")
    static class FileSource implements Source {
    }

    static class SourceProcessor {
        private final Set<Source> sources;
        private final Source httpSource;
        private final Source databaseSource;
        private final Source fileSource;

        @Inject
        SourceProcessor(Set<Source> sources,
                        @Named("http") Source httpSource,
                        @Named("database") Source databaseSource,
                        @Named("file") Source fileSource) {
            this.sources = sources;
            this.httpSource = httpSource;
            this.databaseSource = databaseSource;
            this.fileSource = fileSource;
        }

        public Set<Source> getSources() {
            return sources;
        }

        public Source getDatabaseSource() {
            return databaseSource;
        }

        public Source getHttpSource() {
            return httpSource;
        }

        public Source getFileSource() {
            return fileSource;
        }
    }
}