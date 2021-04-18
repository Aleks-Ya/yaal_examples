import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Использование класса DetailedDiff.
 * DetailedDiff, в отличие от Diff, продолжает сравнение после нахождения первого несоответствия.
 */
public class DetailedDiffUsage {

    @Test
    public void testXMLIdentical() throws IOException, SAXException {
        String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
        String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
        DetailedDiff myDiff = new DetailedDiff(new Diff(myControlXML, myTestXML));

        System.out.println(myDiff.toString());
        assertTrue(myDiff.similar(), "XML similar " + myDiff.toString());
        assertFalse(myDiff.identical(), "XML identical " + myDiff.toString());
    }
}