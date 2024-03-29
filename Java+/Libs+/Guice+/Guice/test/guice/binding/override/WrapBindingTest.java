package guice.binding.override;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class WrapBindingTest {

    /**
     * StackOverflow question: https://stackoverflow.com/questions/66649565/wrap-binding-in-guice
     */
    @Test
    @Disabled("not finished")
    void wrap() {
        var prodInjector = Guice.createInjector(new ProdModule());
        var prodFoo = prodInjector.getInstance(Foo.class);
        assertThat(Mockito.mockingDetails(prodFoo).isMock()).isFalse();

        var testInjector = Guice.createInjector(Modules
                .override(new ProdModule())
                .with(new TestModule()));
        var testFoo = testInjector.getInstance(Foo.class);
        assertThat(Mockito.mockingDetails(testFoo).isMock()).isTrue();
    }

    interface Foo {
    }

    private static class ProdModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Foo.class).to(FooImpl.class);
        }
    }

    private static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
//            Foo prodFoo = getInstanceFromProdModule(); //HOW TO DO IT?
//            bind(Foo.class).toInstance(Mockito.spy(prodFoo));
        }
    }

    static class FooImpl implements Foo {
    }
}