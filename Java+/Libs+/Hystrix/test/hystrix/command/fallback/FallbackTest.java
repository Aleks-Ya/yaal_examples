package hystrix.command.fallback;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FallbackTest {

    @Test
    void fallback() {
        var command = new FallbackCommand();
        var s = command.execute();
        assertThat(s).isEqualTo(FallbackCommand.FALLBACK_RESULT);
        assertThat(command.isResponseFromFallback()).isTrue();
    }
}
