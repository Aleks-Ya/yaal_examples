package pointcut.call.interface_method_call;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
class MyAspect {
    static final String PREFIX = "ASPECT: ";

    @Pointcut("execution(public String pointcut.call.interface_method_call.ConverterInterface.reverse(String))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        return PREFIX + pjp.proceed();
    }
}
