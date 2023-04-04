package logging;

import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

class LoggingTest {
    @Test
    void test() {
        IWork work1 = new Work1();

        Advisor advisor = new DefaultPointcutAdvisor(new DynamicPointcut(), new LogAdvice());

        var pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(work1);

        var proxy = (IWork) pf.getProxy();

        proxy.doSomething(5);
    }
}
