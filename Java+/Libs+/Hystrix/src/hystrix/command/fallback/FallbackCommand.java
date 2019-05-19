package hystrix.command.fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class FallbackCommand extends HystrixCommand<String> {
    static final String FALLBACK_RESULT = "String from fallback";

    FallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
        throw new Exception("Command failed");
    }

    @Override
    protected String getFallback() {
        return FALLBACK_RESULT;
    }
}
