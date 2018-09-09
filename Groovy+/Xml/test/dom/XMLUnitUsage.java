package dom;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Использование статических методов класса XMLUnit.
 */
class XMLUnitUsage {
    private String one = "<plan><week capacity='8' /></plan>";

    private String two = "<plan>\n" +
            "<week capacity='8' />\n" +
            "</plan>";

    @Test
    void compareXml() throws IOException, SAXException {
        Diff diff = XMLUnit.compareXML(one, two);
        assertFalse(diff.similar(), diff.toString());
    }

    @Test
    void buildDocument() throws IOException, SAXException {
        Document oneDoc = XMLUnit.buildTestDocument(one);
        Document twoDoc = XMLUnit.buildControlDocument(two);
        Diff diff = XMLUnit.compareXML(oneDoc, twoDoc);
        assertFalse(diff.similar(), diff.toString());
    }
}