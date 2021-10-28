package configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@SuppressWarnings("unused")
class OneWordProperty {

    @Value("${city}")
    private String city1;

    @Value("#{environment.city}")
    private String city2;

    static void assertEquals(Object actual, Object expected) {
        if (!expected.equals(actual)) {
            throw new AssertionError();
        }
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("City1=" + city1);
        assertEquals(city1, Application.EXPECTED_CITY);

        System.out.println("City2=" + city2);
        assertEquals(city2, Application.EXPECTED_CITY);
    }
}
