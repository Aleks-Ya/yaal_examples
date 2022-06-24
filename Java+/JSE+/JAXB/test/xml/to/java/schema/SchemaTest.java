package xml.to.java.schema;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест парсинга класса Lamp.
 */
class SchemaTest {
    @Test
    void schema() throws JAXBException {
        var is = new ByteArrayInputStream("<lamp  xmlns=\"http://www.w3dchools.com\">little lamp</lamp>".getBytes());
        var jaxbContext = JAXBContext.newInstance(Lamp.class);
        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        var lamp = (Lamp) jaxbUnmarshaller.unmarshal(is);
        assertThat(lamp.getValue()).isEqualTo("little lamp");
    }
}