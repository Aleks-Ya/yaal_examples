package pointcut.annotation.method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HelloMethodAspect {
    @Pointcut("@annotation(pointcut.annotation.method.HelloMethod)")
    public void methodsHelloAnnotated() {
    }

    @Around("methodsHelloAnnotated()")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("Hello, ");
        Object retVal = pjp.proceed();
        System.out.println("!");
        return retVal;
    }
}
