package execution;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {
    @Bean
    MessageCreator messageWriter() {
        var target = new MessageCreator();
        var pf = new ProxyFactory();
        pf.addAdvice(new MessageDecorator());
        pf.setTarget(target);
        return (MessageCreator) pf.getProxy();
    }
}
