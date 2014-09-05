import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class SettingsTest {

    @Test
    public void testToString() throws Exception {
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