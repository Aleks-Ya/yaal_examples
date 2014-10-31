package dom;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

/**
 * Использование статических методов класса XMLUnit.
 */
public class XMLUnitUsage {
    String one = "<plan><week capacity='8' /></plan>";

    String two = "<plan>\n" +
            "<week capacity='8' />\n" +
            "</plan>";

    @Test
    public void compareXml() throws IOException, SAXException {
        Diff diff = XMLUnit.compareXML(one,two);
        assertFalse(diff.toString(), diff.similar());
    }

    @Test
    public void buildDocument() throws IOException, SAXException {
        Document oneDoc = XMLUnit.buildTestDocument(one);
        Document twoDoc = XMLUnit.buildControlDocument(two);
        Diff diff = XMLUnit.compareXML(oneDoc, twoDoc);
        assertFalse(diff.toString(), diff.similar());
    }
}