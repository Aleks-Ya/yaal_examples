package xml.to.java.lamp;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

public class LampTest {

    /**
     * Unmarshall root class has no @XmlRootElement.
     */
    @Test
    public void unmarshallWithoutXmlRootElement() throws JAXBException {
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<lamp type=\"dark\">little lamp</lamp>".getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance(Lamp.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Lamp> element = jaxbUnmarshaller.unmarshal(new StreamSource(is), Lamp.class);
        Lamp line = element.getValue();
        System.out.println(line);
    }

    /**
     * Unmarshall root class has @XmlRootElement.
     */

    @Test
    public void unmarshallWithXmlRootElement() throws JAXBException {
        ByteArrayInputStream is = new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<settings id=\"sets\" port=\"2512\">\n" +
                "    <mask>red mask</mask>\n" +
                "    <priorMask priority=\"1\">blue mask</priorMask>\n" +
                "    <lamps>\n" +
                "        <lamp type=\"dark\">little lamp</lamp>\n" +
                "        <lamp type=\"bright\">big lamp</lamp>\n" +
                "    </lamps>\n" +
                "    <books>\n" +
                "        <book>OS Linux</book>\n" +
                "        <book>OS Windows</book>\n" +
                "    </books>\n" +
                "    <environment>\n" +
                "        <root>the root</root>\n" +
                "        <path>a path</path>\n" +
                "    </environment>\n" +
                "</settings>").getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Settings settings = (Settings) jaxbUnmarshaller.unmarshal(is);
        System.out.println(settings);
    }
}