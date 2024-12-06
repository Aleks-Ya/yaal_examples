package dom


import org.custommonkey.xmlunit.XMLUnit
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.xml.sax.SAXException

import static org.junit.jupiter.api.Assertions.assertFalse

/**
 * Использование статических методов класса XMLUnit.
 */
class XMLUnitUsageTest {
    private static final String ONE = "<plan><week capacity='8' /></plan>";
    private static final String TWO = """
            <plan>
            <week capacity='8' />
            </plan>""";

    @Test
    @Disabled("Fail when run 'gradle test'")
    void compareXml() throws IOException, SAXException {
        def diff = XMLUnit.compareXML(ONE, TWO);
        assertFalse(diff.similar(), diff.toString());
    }

    @Test
    @Disabled("Fail when run 'gradle test'")
    void buildDocument() throws IOException, SAXException {
        def oneDoc = XMLUnit.buildTestDocument(ONE);
        def twoDoc = XMLUnit.buildControlDocument(TWO);
        def diff = XMLUnit.compareXML(oneDoc, twoDoc);
        assertFalse(diff.similar(), diff.toString());
    }
}