package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("city")
public class City {

    @Value("Moscow")
    private String name;

    @Value("1000000")
    private int population;

    @Override
    public String toString() {
        return String.format("scan.City[name='%s' population='%d']", name, population);
    }
}
