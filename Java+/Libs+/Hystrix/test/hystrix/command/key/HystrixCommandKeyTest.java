package hystrix.command.key;

import com.netflix.hystrix.HystrixCommandKey;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HystrixCommandKeyTest {

    @Test
    void instantiate() {
        var keyName = "abc";
        var commandKey = HystrixCommandKey.Factory.asKey(keyName);
        assertThat(commandKey.name()).isEqualTo(HystrixCommandKey.Factory.asKey(keyName).name());
    }
}
