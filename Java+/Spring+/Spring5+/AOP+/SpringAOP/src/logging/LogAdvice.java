package logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.System.out;

public class LogAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        out.println("Invoke: " + method.getDeclaringClass().getName() + "#" + method.getName());
        out.println("Parameters: " + Arrays.deepToString(args));
        Object retVal = invocation.proceed();
        out.println("Return value: " + retVal);
        return retVal;
    }
}
