package oxm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("unused")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UnmarshallerTest.Config.class)
public class UnmarshallerTest {
    @Autowired
    private Unmarshaller unmarshaller;

    @Test
    void marshall() throws IOException {
        String xml = "<oxm.Person><name>John</name><age>30</age></oxm.Person>";
        Person expPerson = new Person("John", 30);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
        Source source = new StreamSource(is);
        Person actPerson = (Person) unmarshaller.unmarshal(source);
        assertThat(actPerson, equalTo(expPerson));
    }

    @Configuration
    static class Config {
        @Bean
        public Unmarshaller unmarshaller() {
            return new XStreamMarshaller();
        }
    }
}

