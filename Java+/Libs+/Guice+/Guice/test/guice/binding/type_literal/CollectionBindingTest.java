package guice.binding.type_literal;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionBindingTest {
    private static final Collection<String> COLLECTION = Arrays.asList("A", "B");

    @Test
    void bind() {
        var injector = Guice.createInjector(new CollectionModule());
        var holder = injector.getInstance(CollectionHolder.class);
        var collection = holder.getCollection();
        assertThat(collection).isEqualTo(COLLECTION);
    }

    private static class CollectionModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(new TypeLiteral<Collection<String>>() {
            }).toInstance(COLLECTION);
        }
    }

    public static class CollectionHolder {
        private final Collection<String> collection;

        @Inject
        public CollectionHolder(Collection<String> collection) {
            this.collection = collection;
        }

        Collection<String> getCollection() {
            return collection;
        }
    }
}