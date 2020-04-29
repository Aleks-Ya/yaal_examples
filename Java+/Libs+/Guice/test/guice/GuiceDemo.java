package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Source: https://github.com/google/guice/wiki/GettingStarted#a-simple-guice-application
 */
public class GuiceDemo {
    @Qualifier
    @Retention(RUNTIME)
    @interface Message {
    }

    @Qualifier
    @Retention(RUNTIME)
    @interface Count {
    }

    /**
     * Guice module that provides bindings for message and count used in
     * {@link Greeter}.
     */
    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Key.get(String.class, Message.class)).toInstance("hello world");
        }

        @Provides
        @Count
        static Integer provideCount() {
            return 3;
        }
    }

    static class Greeter {
        private final String message;
        private final int count;

        @Inject
        Greeter(@Message String message, @Count int count) {
            this.message = message;
            this.count = count;
        }

        void sayHello() {
            for (int i = 0; i < count; i++) {
                System.out.println(message);
            }
        }
    }

    @Test
    public void test() {
        Injector injector = Guice.createInjector(new DemoModule());
        Greeter greeter = injector.getInstance(Greeter.class);
        greeter.sayHello();
    }
}
