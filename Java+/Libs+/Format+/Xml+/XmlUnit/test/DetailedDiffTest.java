import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Использование класса DetailedDiff.
 * DetailedDiff, в отличие от Diff, продолжает сравнение после нахождения первого несоответствия.
 */
class DetailedDiffTest {

    @Test
    void testXMLIdentical() throws IOException, SAXException {
        var myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
        var myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
        var myDiff = new DetailedDiff(new Diff(myControlXML, myTestXML));

        System.out.println(myDiff);
        assertThat(myDiff.similar()).withFailMessage("XML similar " + myDiff).isTrue();
        assertThat(myDiff.identical()).withFailMessage("XML identical " + myDiff).isFalse();
    }
}