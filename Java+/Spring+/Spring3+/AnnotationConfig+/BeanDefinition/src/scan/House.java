package scan;

import newbean.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class House {
    @Autowired
    private ApplicationContext context;

//    @Autowired
//    private Address address;

    public Address getAddress() {
        return context.getBean(Address.class);
    }

//    @Override
//    public String toString() {
//        return "Дом " + address + " " + context;
//    }
}
