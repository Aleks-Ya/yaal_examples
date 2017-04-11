package xml.to.java.schema;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Тест парсинга класса Lamp.
 */
public class SchemaTest {
    @Test
    public void schema() throws JAXBException {
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<lamp  xmlns=\"http://www.w3dchools.com\">little lamp</lamp>".getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance(Lamp.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Lamp lamp = (Lamp) jaxbUnmarshaller.unmarshal(is);
        assertThat(lamp.getValue(), equalTo("little lamp"));
    }
}