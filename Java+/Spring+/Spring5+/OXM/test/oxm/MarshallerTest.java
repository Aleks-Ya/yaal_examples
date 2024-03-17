package oxm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unused")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MarshallerTest.Config.class)
class MarshallerTest {
    @Autowired
    private Marshaller marshaller;

    @Test
    void marshall() throws IOException {
        var person = new Person("John", 30);
        var os = new ByteArrayOutputStream();
        Result result = new StreamResult(os);
        marshaller.marshal(person, result);
        var act = os.toString();
        var exp = "<oxm.Person><name>John</name><age>30</age></oxm.Person>";
        assertThat(act).isEqualTo(exp);
    }

    @Configuration
    static class Config {
        @Bean
        public Marshaller marshaller() {
            return new XStreamMarshaller();
        }
    }
}

