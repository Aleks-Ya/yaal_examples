package guice.binding.just_in_time;

import com.google.inject.Guice;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Docs: https://github.com/google/guice/wiki/JustInTimeBindings
 */
public class JustInTimeBindingTest {
    private static final String TEXT = "hi";

    @Test
    public void bind() {
        var injector = Guice.createInjector();
        var generator = injector.getInstance(TextGenerator.class);
        var text = generator.generate();
        assertThat(text, equalTo(TEXT));
    }

    private static class TextGenerator {
        String generate() {
            return TEXT;
        }
    }
}