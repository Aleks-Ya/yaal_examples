package profile.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Printer implements InitializingBean {
    @Autowired
    IMessageProvider provider;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(provider.getMessage());
    }
}
