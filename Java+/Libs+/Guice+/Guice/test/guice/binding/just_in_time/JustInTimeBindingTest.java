package guice.binding.just_in_time;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Docs: https://github.com/google/guice/wiki/JustInTimeBindings
 */
public class JustInTimeBindingTest {
    private static final String TEXT = "hi";

    private static class TextGenerator {
        String generate() {
            return TEXT;
        }
    }

    @Test
    public void bind() {
        Injector injector = Guice.createInjector();
        TextGenerator generator = injector.getInstance(TextGenerator.class);
        String text = generator.generate();
        assertThat(text, equalTo(TEXT));
    }
}