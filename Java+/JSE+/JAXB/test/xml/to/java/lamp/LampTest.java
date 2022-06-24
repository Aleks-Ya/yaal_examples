package xml.to.java.lamp;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

class LampTest {

    /**
     * Unmarshall root class has no @XmlRootElement.
     */
    @Test
    void unmarshallWithoutXmlRootElement() throws JAXBException {
        var is = new ByteArrayInputStream(
                "<lamp type=\"dark\">little lamp</lamp>".getBytes());
        var jaxbContext = JAXBContext.newInstance(Lamp.class);

        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        var element = jaxbUnmarshaller.unmarshal(new StreamSource(is), Lamp.class);
        var line = element.getValue();
        System.out.println(line);
    }

    /**
     * Unmarshall root class has @XmlRootElement.
     */
    @Test
    void unmarshallWithXmlRootElement() throws JAXBException {
        var is = new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
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
        var jaxbContext = JAXBContext.newInstance(Settings.class);

        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        var settings = (Settings) jaxbUnmarshaller.unmarshal(is);
        System.out.println(settings);
    }
}