package configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static configuration.properties.OneWordProperty.assertEquals;

@Component
@SuppressWarnings("unused")
class TwoWordsProperty {

    @Value("${person.name}")
    private String name1;

    @Value("#{environment['person.name']}")
    private String name2;

    @PostConstruct
    public void postConstruct() {
        System.out.println("Name1=" + name1);
        assertEquals(name1, Application.EXPECTED_NAME);

        System.out.println("Name2=" + name2);
        assertEquals(name2, Application.EXPECTED_NAME);
    }
}
