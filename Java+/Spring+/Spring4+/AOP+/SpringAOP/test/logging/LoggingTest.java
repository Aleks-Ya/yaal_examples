package logging;

import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class LoggingTest {

    @Test
    public void test() {
        IWork work1 = new Work1();

        Advisor advisor = new DefaultPointcutAdvisor(new DynamicPointcut(), new LogAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(work1);

        IWork proxy = (IWork) pf.getProxy();

        proxy.doSomething(5);
    }
}
