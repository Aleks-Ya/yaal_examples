package provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Printer {
    @Autowired
    IMessageProvider provider;

    @PostConstruct
    public void print() {
        System.out.println(provider.getMessage());
    }
}
