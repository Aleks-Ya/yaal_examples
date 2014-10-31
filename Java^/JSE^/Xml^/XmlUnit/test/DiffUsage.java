import org.custommonkey.xmlunit.Diff;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Использование класса Diff.
 * Diff, в отличие от DetailedDiff, прекращает сравнение после нахождения первого несоответствия.
 */
public class DiffUsage {

    @Test
    public void testXMLIdentical() throws IOException, SAXException {
        String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
        String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
        Diff myDiff = new Diff(myControlXML, myTestXML);

        System.out.println(myDiff.toString());
        assertTrue("XML similar " + myDiff.toString(), myDiff.similar());
        assertFalse("XML identical " + myDiff.toString(), myDiff.identical());
    }
}