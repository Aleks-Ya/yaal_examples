package pointcut.call.interface_method_call;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
class MyAspect {
    static final String PREFIX = "ASPECT: ";

    @Around("execution(public String pointcut.call.interface_method_call.ConverterInterface.reverse(String))")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        return PREFIX + pjp.proceed();
    }
}
