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
public class HelloAspect {
    @Pointcut("execution(* pointcut.execution.MessageWriter.*(..))")
    public void allMessageWriterMethods() {
    }

    @Around("allMessageWriterMethods()")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("Hello, ");
        Object retVal = pjp.proceed();
        System.out.println("!");
        return retVal;
    }
}
