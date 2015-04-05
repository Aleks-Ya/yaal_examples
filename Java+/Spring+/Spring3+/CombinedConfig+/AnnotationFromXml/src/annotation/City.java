package annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import annotation.mayor.Mayor;
import xml.Airport;

import javax.annotation.Resource;

@Service("city")
public class City {
    @Value("Спб")
    private String name;
    @Autowired
    private Mayor mayor;

    @Resource
    private Mayor mayor2;

    @Autowired
    @Qualifier("rur")
    private Currency currency;

    @Autowired
    @Qualifier("airportXml")
    private Airport airport;

    @Override
    public String toString() {
        return "name=" + name + "\n" +
                "mayor=" + mayor + "\n" +
                "mayor2=" + mayor2 + "\n" +
                "airport=" + airport + "\n" +
                "currency=" + currency + "\n";
    }
}
