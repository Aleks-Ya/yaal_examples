package beans;

import org.springframework.beans.factory.annotation.Autowired;

public class House {
    @Autowired
    private Address address;

    public Address getAddress() {
        return address;
    }
}
