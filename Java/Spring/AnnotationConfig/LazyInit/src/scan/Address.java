package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Address {

    @Autowired
    Street street;

    public Address() {
        System.out.println("Address is constructed");
    }

}
