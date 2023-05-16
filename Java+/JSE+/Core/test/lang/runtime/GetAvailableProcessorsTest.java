package lang.runtime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Get number of CPUs available for current Java app.
 */
class GetAvailableProcessorsTest {

    @Test
    void availableProcessors() {
        var processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU number: " + processors);
        assertThat(processors).isPositive();
    }
}
