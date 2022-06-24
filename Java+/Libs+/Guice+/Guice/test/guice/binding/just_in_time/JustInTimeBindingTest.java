package guice.binding.just_in_time;

import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Docs: https://github.com/google/guice/wiki/JustInTimeBindings
 */
class JustInTimeBindingTest {
    private static final String TEXT = "hi";

    @Test
    void bind() {
        var injector = Guice.createInjector();
        var generator = injector.getInstance(TextGenerator.class);
        var text = generator.generate();
        assertThat(text).isEqualTo(TEXT);
    }

    private static class TextGenerator {
        String generate() {
            return TEXT;
        }
    }
}