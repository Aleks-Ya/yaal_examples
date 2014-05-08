package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("city")
public class City {

    @Value("Moscow")
    private String name;

    @Value("1000000")
    private int population;

    @Autowired
    private Mayor mayor;

    @Resource
    private Mayor mayor2;

    @Override
    public String toString() {
        return String.format("scan.City[name='%s' population='%d' mayor='%s' mayor2='%s']", name, population, mayor, mayor2);
    }
}
