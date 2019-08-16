package method_execution_time.custom;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

public class MethodProfilerAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = createInvocationTraceName(invocation);
        StopWatch stopWatch = new StopWatch(name);
        stopWatch.start(name);
        try {
            return invocation.proceed();
        } finally {
            stopWatch.stop();
            Logger logger = LoggerFactory.getLogger(invocation.getThis().getClass());
            logger.info("Execution time {}: {}", name, stopWatch.getTotalTimeMillis());
        }
    }

    private String createInvocationTraceName(MethodInvocation invocation) {
        StringBuilder sb = new StringBuilder();
        Method method = invocation.getMethod();
        Class<?> clazz = invocation.getThis().getClass();
        sb.append(clazz.getName());
        sb.append('.').append(method.getName());
        return sb.toString();
    }

}
