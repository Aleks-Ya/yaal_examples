package logging;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class LoggingMain {
    public static void main(String[] args) {
        IWork work1 = new Work1();

        Advisor advisor = new DefaultPointcutAdvisor(new DynamicPointcut(), new LogAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(work1);

        IWork proxy = (IWork) pf.getProxy();

        proxy.doSomething(5);
    }
}
