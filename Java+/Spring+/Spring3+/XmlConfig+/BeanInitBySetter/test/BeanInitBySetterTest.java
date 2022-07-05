import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class BeanInitBySetterTest {

    @Autowired
    private City city;

    @Test
    void test() {
        assertThat(city.getName()).isEqualTo("SPb");
        assertThat(city.getCountry()).isEqualTo(CountryEnum.RUSSIA);
        assertThat(city.getPopulation()).isEqualTo(1000000);
    }
}