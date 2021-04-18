package hystrix.request.cache;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class RequestCacheTest {

    @Test
    public void cache() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            String cacheKey = "my_key";
            assertThat(RequestCacheCommand.executionCounter, equalTo(0));
            {
                final RequestCacheCommand command = new RequestCacheCommand(cacheKey);
                String s = command.execute();
                assertThat(RequestCacheCommand.executionCounter, equalTo(1));
                assertThat(s, equalTo("Hello!"));
                assertFalse(command.isResponseFromCache());
            }
            {
                final RequestCacheCommand command = new RequestCacheCommand(cacheKey);
                String s = command.execute();
                assertThat(RequestCacheCommand.executionCounter, equalTo(1));
                assertThat(s, equalTo("Hello!"));
                assertTrue(command.isResponseFromCache());
            }
        } finally {
            context.shutdown();
        }
    }
}
