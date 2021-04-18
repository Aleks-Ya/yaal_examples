package xsd.validation;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XsdValidationTest {

    @Test
    public void validate() throws IOException, SAXException {
        File xml = new File(getClass().getResource("data.xml").getFile());
        File xsd = new File(getClass().getResource("schema.xsd").getFile());
        validate(xml, xsd);//No exception - ok
    }

    private static void validate(File xmlFile, File xsd) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsd);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(xmlFile);
        validator.validate(source);
    }
}