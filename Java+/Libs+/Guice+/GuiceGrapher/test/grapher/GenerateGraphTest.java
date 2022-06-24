package grapher;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.grapher.graphviz.GraphvizGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import com.google.inject.multibindings.Multibinder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Set;

class GenerateGraphTest {

    @Test
    void bind() throws IOException {
        var graphvizInjector = Guice.createInjector(new GraphvizModule());
        var outputFile = Files.createTempFile(GenerateGraphTest.class.getSimpleName(), ".dot");
        System.out.println("Output file: " + outputFile);

        var out = new PrintWriter(outputFile.toFile());

        var grapher = graphvizInjector.getInstance(GraphvizGrapher.class);
        grapher.setOut(out);
        grapher.setRankdir("TB");

        var inspectedInjector = Guice.createInjector(new RootModule());
        grapher.graph(inspectedInjector);
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