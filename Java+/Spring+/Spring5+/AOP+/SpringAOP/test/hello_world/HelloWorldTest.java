package hello_world;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

class HelloWorldTest {

    @Test
    void test() {
        var target = new MessageWriter();

        var pf = new ProxyFactory();
        pf.addAdvice(new MessageDecorator());
        pf.setTarget(target);

        var proxy = (MessageWriter) pf.getProxy();

        target.writeMessage();
        System.out.println();
        proxy.writeMessage();
    }
}
