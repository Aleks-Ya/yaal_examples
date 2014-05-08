package scan2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scan1.Address;

@Component
public class House {

    @Autowired
    private Address address;

    @Override
    public String toString() {
        return "Дом " + address;
    }
}
