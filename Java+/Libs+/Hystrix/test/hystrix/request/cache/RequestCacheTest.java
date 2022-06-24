package hystrix.request.cache;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestCacheTest {

    @Test
    void cache() {
        var context = HystrixRequestContext.initializeContext();
        try {
            var cacheKey = "my_key";
            assertThat(RequestCacheCommand.executionCounter).isEqualTo(0);
            {
                final var command = new RequestCacheCommand(cacheKey);
                var s = command.execute();
                assertThat(RequestCacheCommand.executionCounter).isEqualTo(1);
                assertThat(s).isEqualTo("Hello!");
                assertThat(command.isResponseFromCache()).isFalse();
            }
            {
                final var command = new RequestCacheCommand(cacheKey);
                var s = command.execute();
                assertThat(RequestCacheCommand.executionCounter).isEqualTo(1);
                assertThat(s).isEqualTo("Hello!");
                assertThat(command.isResponseFromCache()).isTrue();
            }
        } finally {
            context.shutdown();
        }
    }
}
