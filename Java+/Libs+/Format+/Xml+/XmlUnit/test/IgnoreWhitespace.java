import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Игнорирование пробелов и переносов строк при сравнении XML.
 */
public class IgnoreWhitespace {
    String one = "<plan><week capacity='8' /></plan>";

    String two = "<plan>\n" +
            "<week capacity='8' />\n" +
            "</plan>";

    @Test
    public void ignore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        DetailedDiff diff = new DetailedDiff(new Diff(one, two));
        assertTrue(diff.similar(), diff.toString());

    }

    @Test
    public void notIgnore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(false);
        DetailedDiff diff = new DetailedDiff(new Diff(one, two));
        assertFalse(diff.similar(), diff.toString());
    }
}