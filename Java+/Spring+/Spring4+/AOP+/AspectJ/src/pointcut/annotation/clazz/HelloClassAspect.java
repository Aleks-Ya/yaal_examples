package pointcut.annotation.clazz;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class HelloClassAspect {

    @Around("within(@pointcut.annotation.clazz.HelloClass *)")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("Hello, ");
        Object retVal = pjp.proceed();
        System.out.println("!");
        return retVal;
    }
}
