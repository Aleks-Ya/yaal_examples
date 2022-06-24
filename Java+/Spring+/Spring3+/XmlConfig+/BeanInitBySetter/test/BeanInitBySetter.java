import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BeanInitBySetter {

    @Autowired
    private City city;

    @Test
    void test() {
        assertEquals("SPb", city.getName());
        assertEquals(CountryEnum.RUSSIA, city.getCountry());
        assertEquals(1000000, city.getPopulation());
    }
}