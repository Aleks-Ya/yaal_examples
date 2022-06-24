import org.custommonkey.xmlunit.Diff;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Использование класса Diff.
 * Diff, в отличие от DetailedDiff, прекращает сравнение после нахождения первого несоответствия.
 */
class DiffUsageTest {

    @Test
    void testXMLIdentical() throws IOException, SAXException {
        String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
        String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
        Diff myDiff = new Diff(myControlXML, myTestXML);

        System.out.println(myDiff);
        assertThat(myDiff.similar()).withFailMessage("XML similar " + myDiff).isTrue();
        assertThat(myDiff.identical()).withFailMessage("XML identical " + myDiff).isFalse();
    }
}