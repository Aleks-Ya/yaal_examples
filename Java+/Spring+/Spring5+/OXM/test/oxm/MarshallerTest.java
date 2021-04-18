package oxm;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MarshallerTest.Config.class)
public class MarshallerTest {
    @Autowired
    private Marshaller marshaller;

    @Test
    public void marshall() throws IOException {
        Person person = new Person("John", 30);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Result result = new StreamResult(os);
        marshaller.marshal(person, result);
        String act = new String(os.toByteArray());
        String exp = "<oxm.Person><name>John</name><age>30</age></oxm.Person>";
        assertThat(act, equalTo(exp));
    }

    @Configuration
    static class Config {
        @Bean
        public Marshaller marshaller() {
            return new XStreamMarshaller();
        }
    }
}

