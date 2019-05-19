package hystrix.command.key;

import com.netflix.hystrix.HystrixCommandKey;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class HystrixCommandKeyTest {

    @Test
    public void instantiate() {
        String keyName = "abc";
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey(keyName);
        assertThat(commandKey.name(), equalTo(HystrixCommandKey.Factory.asKey(keyName)));
    }
}
