package hello_world;

import org.springframework.aop.framework.ProxyFactory;

public class HelloWorldExample {
    public static void main(String[] args) {
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
