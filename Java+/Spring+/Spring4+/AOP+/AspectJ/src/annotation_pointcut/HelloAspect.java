package annotation_pointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HelloAspect {
    @Pointcut("@annotation(annotation_pointcut.Hello)")
    public void helloAnnotation() {
    }

    @Around("helloAnnotation()")
    public Object addHello(ProceedingJoinPoint pjp) throws Throwable {
        System.out.print("Hello ");
        Object retVal = pjp.proceed();
        System.out.println("!");
        return retVal;
    }
}
