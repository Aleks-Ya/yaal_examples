package mybeans;

import javax.ejb.Stateless;

@Stateless(name = "HelloWorldEJB")
public class HelloWorldBean {
    public HelloWorldBean() {
    }

    public String sayHello() {
        return "Hello, World!";
    }
}