package hello_world;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class HelloWorldTest {

    @Test
    public void test() {
        MessageWriter target = new MessageWriter();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new MessageDecorator());
        pf.setTarget(target);

        MessageWriter proxy = (MessageWriter) pf.getProxy();

        target.writeMessage();
        System.out.println();
        proxy.writeMessage();
    }
}
