package hystrix.request.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class RequestCacheCommand extends HystrixCommand<String> {
    public static int executionCounter;
    private final String cacheKey;

    public RequestCacheCommand(String cacheKey) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.cacheKey = cacheKey;
    }

    @Override
    protected String run() {
        executionCounter++;
        return "Hello!";
    }

    @Override
    protected String getCacheKey() {
        return cacheKey;
    }
}
