package scan;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

@Component //нужен, чтобы сработал @ImportResource
@Lazy//нужен, чтобы повторно не вызывались @PostConstruct и @PreDestroy (они будут вызваны из xml)
@ImportResource("classpath:xml-context.xml")
public class Address implements InitializingBean, DisposableBean {

    //    @PostConstruct
    public void postConstruct() {
        System.out.println("#1 Address#postConstruct()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("#2 Address#afterPropertiesSet()");
    }

    public void initMethod() {
        System.out.println("#3 Address#initMethod()");
    }

    //    @PreDestroy
    public void preDestroy() {
        System.out.println("#1 Address#preDestroy()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("#2 Address#destroy()");
    }

    public void destroyMethod() {
        System.out.println("#3 Address#initMethod()");
    }
}