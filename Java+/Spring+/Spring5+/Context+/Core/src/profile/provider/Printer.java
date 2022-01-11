package profile.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Printer implements InitializingBean {
    @Autowired
    IMessageProvider provider;

    @Override
    public void afterPropertiesSet() {
        System.out.println(provider.getMessage());
    }
}
