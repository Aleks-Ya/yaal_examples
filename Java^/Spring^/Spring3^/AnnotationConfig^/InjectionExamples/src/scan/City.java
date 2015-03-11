package scan;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import scan.mayor.Mayor;
import scan.person.IPerson;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    @Qualifier("pertosian")
    private IPerson petrosian;

    @Autowired
    @Qualifier("zadornov1")
    private IPerson zadornov1;

    @Autowired
    @Qualifier("zadornov2")
    private IPerson zadornov2;

    /**
     * Внедрение массива всех реализаций интерфейса.
     */
    @Autowired
    private IPerson[] personArray;

    /**
     * Внедрение списка всех реализаций интерфейса.
     */
    @Autowired
    private List<IPerson> personList;

    /**
     * Внедрение множества всех реализаций интерфейса.
     */
    @Autowired
    private Set<IPerson> personSet;

    /**
     * Внедрение мапы всех реализаций интерфейса.
     */
    @Autowired
    private Map<String, IPerson> personMap;

    /**
     * Внедрение по стандартному интерфейсу ApplicationContext.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Внедрение по стандартному интерфейсу BeanFactory.
     */
    @Autowired
    private BeanFactory beanFactory;

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
        builder.append("population=").append(population).append("\n");
        builder.append("mayor=").append(mayor).append("\n");
        builder.append("mayor2=").append(mayor2).append("\n");
        builder.append("airport=").append(airport).append("\n");
        builder.append("currency=").append(currency).append("\n");
        builder.append("petrosian=").append(petrosian).append("\n");
        builder.append("zadornov1=").append(zadornov1).append("\n");
        builder.append("zadornov2=").append(zadornov2).append("\n");
        builder.append("personArray=").append(Arrays.deepToString(personArray)).append("\n");
        builder.append("personList=").append(personList).append("\n");
        builder.append("personSet=").append(personSet).append("\n");
        builder.append("personMap=").append(personMap).append("\n");
        builder.append("applicationContext=").append(applicationContext).append("\n");
        builder.append("beanFactory=").append(beanFactory).append("\n");
        return builder.toString();
    }
}
