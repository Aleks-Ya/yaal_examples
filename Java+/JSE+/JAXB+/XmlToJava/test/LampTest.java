import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Тест парсинга класса Lamp.
 */
public class LampTest {

    @Test
    public void lamp() throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream("<lamp  xmlns=\"http://www.w3dchools.com\" type=\"dark\">little lamp</lamp>".getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance(Lamp.class, LampType.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Lamp line = (Lamp) jaxbUnmarshaller.unmarshal(is);
        System.out.println(line);
    }
}