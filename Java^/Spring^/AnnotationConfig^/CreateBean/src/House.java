import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import scan.Address;

public class House {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private Address address;

    @Override
    public String toString() {
        return "Дом " + address + " " + context;
    }
}
