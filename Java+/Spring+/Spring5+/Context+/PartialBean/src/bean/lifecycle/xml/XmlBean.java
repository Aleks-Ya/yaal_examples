package bean.lifecycle.xml;

import bean.lifecycle.MyBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class XmlBean implements InitializingBean, DisposableBean {

    @Autowired
    public XmlBean(MyBean bean) {
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

    public void afterPropertiesSet() throws Exception {
        System.out.println("#4 Initializing#afterPropertiesSet()");
    }

    public void defaultInit() {
        System.out.println("#5 defaultInit");
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("#6 @PreDestroy");
    }

    public void destroy() throws Exception {
        System.out.println("#7 DisposableBean#destroy()");
    }

    public void defaultDestroy() {
        System.out.println("#8 defaultDestroy");
    }
}