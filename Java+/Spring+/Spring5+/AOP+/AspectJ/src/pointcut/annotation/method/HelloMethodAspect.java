package pointcut.annotation.method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class HelloMethodAspect {

    @Around("@annotation(pointcut.annotation.method.HelloMethod)")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("Hello, ");
        Object retVal = pjp.proceed();
        System.out.println("!");
        return retVal;
    }
}
