package guice.binding.type_literal;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CollectionBindingTest {
    private static final Collection<String> COLLECTION = Arrays.asList("A", "B");

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

    @Test
    public void bind() {
        Injector injector = Guice.createInjector(new CollectionModule());
        CollectionHolder holder = injector.getInstance(CollectionHolder.class);
        Collection<String> collection = holder.getCollection();
        assertThat(collection, equalTo(COLLECTION));
    }
}