package pointcut.expression;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAdvice implements MethodInterceptor {
    static boolean invoked = false;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        invoked = true;
        return invocation.proceed();
    }
}
