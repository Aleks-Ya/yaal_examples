package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scan.mayor.Mayor;

import javax.annotation.Resource;

@Service("city")
public class City {
    private String name;
    @Autowired
    private Mayor mayor;

    @Resource
    private Mayor mayor2;

    @Autowired
    @Qualifier("rur")
    private Currency currency;

    private Airport airport;

    @Autowired
    public City(Airport airport, @Value("Спб") String name) {
        this.airport = airport;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name=").append(name).append("\n");
        builder.append("mayor=").append(mayor).append("\n");
        builder.append("mayor2=").append(mayor2).append("\n");
        builder.append("airport=").append(airport).append("\n");
        builder.append("currency=").append(currency).append("\n");
        return builder.toString();
    }
}
