package logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

import static java.lang.System.out;

class LogAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        var method = invocation.getMethod();
        var args = invocation.getArguments();
        out.println("Invoke: " + method.getDeclaringClass().getName() + "#" + method.getName());
        out.println("Parameters: " + Arrays.deepToString(args));
        var retVal = invocation.proceed();
        out.println("Return value: " + retVal);
        return retVal;
    }
}
