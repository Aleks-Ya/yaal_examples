package xsd.validation;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

class XsdValidationTest {

    private static void validate(File xmlFile, File xsd) throws SAXException, IOException {
        var schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        var schema = schemaFactory.newSchema(xsd);
        var validator = schema.newValidator();
        Source source = new StreamSource(xmlFile);
        validator.validate(source);
    }

    @Test
    void validate() throws IOException, SAXException {
        var xml = new File(getClass().getResource("data.xml").getFile());
        var xsd = new File(getClass().getResource("schema.xsd").getFile());
        validate(xml, xsd);//No exception - ok
    }
}