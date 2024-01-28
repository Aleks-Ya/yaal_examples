package guice.binding.annotation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Key;
import org.junit.jupiter.api.Test;

import jakarta.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationBindingTest {

    @Test
    void bind() {
        var injector = Guice.createInjector(new DemoModule());
        var person = injector.getInstance(Person.class);
        var message = person.getMessage();
        assertThat(message).isEqualTo("hello and bye");
    }

    @Qualifier
    @Retention(RUNTIME)
    @interface Greeting {
    }

    @Qualifier
    @Retention(RUNTIME)
    @interface Leaving {
    }

    private static class DemoModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Key.get(String.class, Greeting.class)).toInstance("hello");
            bind(Key.get(String.class, Leaving.class)).toInstance("bye");
        }
    }

    static class Person {
        private final String greetingMessage;
        private final String leavingMessage;

        @Inject
        Person(@Greeting String greetingMessage, @Leaving String leavingMessage) {
            this.greetingMessage = greetingMessage;
            this.leavingMessage = leavingMessage;
        }

        String getMessage() {
            return greetingMessage + " and " + leavingMessage;
        }
    }
}