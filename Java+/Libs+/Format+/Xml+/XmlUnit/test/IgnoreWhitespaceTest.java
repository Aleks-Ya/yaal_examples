import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Игнорирование пробелов и переносов строк при сравнении XML.
 */
class IgnoreWhitespaceTest {
    String one = "<plan><week capacity='8' /></plan>";

    String two = "<plan>\n" +
            "<week capacity='8' />\n" +
            "</plan>";

    @Test
    void ignore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        var diff = new DetailedDiff(new Diff(one, two));
        assertThat(diff.similar()).isTrue();
    }

    @Test
    void notIgnore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(false);
        var diff = new DetailedDiff(new Diff(one, two));
        assertThat(diff.similar()).isFalse();
    }
}