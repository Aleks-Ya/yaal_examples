import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, SAXException {
        File xml = new File(Main.class.getResource("data.xml").getFile());
        File xsd = new File(Main.class.getResource("schema.xsd").getFile());
        try {
            validate(xml, xsd);
            System.out.println("XML is valid");
        } catch (org.xml.sax.SAXParseException e) {
            System.out.println("XML INVALID!!!!!!!!!!!!!!!!!");
            System.out.println(e.getMessage());
        }
    }

    private static void validate(File xmlFile, File xsd) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsd);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(xmlFile);
        validator.validate(source);
    }
}