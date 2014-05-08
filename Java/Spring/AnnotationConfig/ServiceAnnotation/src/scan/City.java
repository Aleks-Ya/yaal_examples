package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scan.person.IPerson;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("city")
public class City {

    private String name;

    @Value("1000000")
    private int population;

    @Autowired
    private Mayor mayor;

    @Resource
    private Mayor mayor2;

    @Autowired
    private IPerson[] persons;

    @Autowired
    private List<IPerson> persons2;

    @Autowired
    private Set<IPerson> persons3;

    private Airport airport;

    @Autowired
    public City(Airport airport, @Value("Спб") String name) {
        this.airport = airport;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name=").append(name);
        builder.append("population=").append(population);
        builder.append("mayor=").append(mayor);
        builder.append("mayor2=").append(mayor2);
        builder.append("airport=").append(airport).append("\n");
        builder.append("persons=").append(Arrays.deepToString(persons)).append("\n");
        builder.append("persons2=").append(persons2).append("\n");
        builder.append("persons3=").append(persons3).append("\n");
        return builder.toString();
    }
}
