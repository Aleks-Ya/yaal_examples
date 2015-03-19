package annotation;

import bean.MyBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class AnnotationBean implements InitializingBean, DisposableBean {

    @Autowired
    public AnnotationBean(MyBean bean) {
        System.out.println("#1 constructor bean=" + bean);
    }

    @Autowired
    public void withParameter(MyBean bean) {
        System.out.println("#2 Autowired#withParameter() bean=" + bean);
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        System.out.println("#3 @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("#4 Initializing#afterPropertiesSet()");
    }


    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("#5 @PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("#6 DisposableBean#destroy()");
    }
}