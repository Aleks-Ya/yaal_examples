package context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE, classes = Config.class)
@TestPropertySource(properties = "PREVIOUS_URI=http://m.city.ru:7777/info")
class ConfigurationPropertiesTest {

    @Autowired
    private Person person;

    @Autowired
    private City city;

    @Autowired
    private AllInfo allInfo;

    @Test
    void person() {
        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getAge()).isEqualTo(30);
    }

    @Test
    void city() {
        assertThat(city.toString())
                .isEqualTo("Moscow-1147 at http://moscow.russia.ru:8080/info or at http://m.city.ru:7777/info");
        assertThat(city.getUri())
                .isEqualTo(URI.create("http://moscow.russia.ru:8080/info"));
    }

    @Test
    void allInfo() {
        assertThat(allInfo.toString())
                .isEqualTo("John-30 from Moscow-1147 at http://moscow.russia.ru:8080/info or at http://m.city.ru:7777/info");
    }

}