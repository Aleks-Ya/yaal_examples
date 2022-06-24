package hystrix.command.key;

import com.netflix.hystrix.HystrixCommandKey;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HystrixCommandKeyTest {

    @Test
    void instantiate() {
        String keyName = "abc";
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey(keyName);
        assertThat(commandKey.name(), equalTo(HystrixCommandKey.Factory.asKey(keyName)));
    }
}
