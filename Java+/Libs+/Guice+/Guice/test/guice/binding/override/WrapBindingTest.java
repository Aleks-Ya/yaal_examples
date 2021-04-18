package guice.binding.override;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WrapBindingTest {

    /**
     * StackOverflow question: https://stackoverflow.com/questions/66649565/wrap-binding-in-guice
     */
    @Test
    @Disabled("not finished")
    public void wrap() {
        Injector prodInjector = Guice.createInjector(new ProdModule());
        Foo prodFoo = prodInjector.getInstance(Foo.class);
        assertFalse(Mockito.mockingDetails(prodFoo).isMock());

        Injector testInjector = Guice.createInjector(Modules
                .override(new ProdModule())
                .with(new TestModule()));
        Foo testFoo = testInjector.getInstance(Foo.class);
        assertTrue(Mockito.mockingDetails(testFoo).isMock());
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

    interface Foo {
    }

    static class FooImpl implements Foo {
    }
}