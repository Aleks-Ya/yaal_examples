package bean.definition;

import org.springframework.beans.factory.annotation.Autowired;

class House {
    @Autowired
    private Address address;

    public Address getAddress() {
        return address;
    }
}
