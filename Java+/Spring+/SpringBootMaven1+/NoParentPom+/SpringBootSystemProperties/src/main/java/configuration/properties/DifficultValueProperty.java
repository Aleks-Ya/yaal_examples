package configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static configuration.properties.OneWordProperty.assertEquals;

@Component
@SuppressWarnings("unused")
class DifficultValueProperty {

    @Value("${connection.url}")
    private String url1;

    @Value("#{environment['connection.url']}")
    private String url2;

    @PostConstruct
    public void postConstruct() {
        System.out.println("Url1=" + url1);
        assertEquals(url1, Application.EXPECTED_URL);

        System.out.println("Url2=" + url2);
        assertEquals(url2, Application.EXPECTED_URL);
    }
}
