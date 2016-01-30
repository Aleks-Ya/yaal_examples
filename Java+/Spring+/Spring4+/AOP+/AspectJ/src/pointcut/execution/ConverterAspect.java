package pointcut.execution;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class ConverterAspect {
    static final String PREFIX = "ASPECT: ";

    @Pointcut("execution(public String pointcut.execution.Converter.toUpperCase(String))")
    public void allMessageWriterMethods() {
    }

    @Around("allMessageWriterMethods()")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        return PREFIX + pjp.proceed();
    }
}
