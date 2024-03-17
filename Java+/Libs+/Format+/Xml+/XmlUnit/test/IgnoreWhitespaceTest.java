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
    private static final String ONE = "<plan><week capacity='8' /></plan>";

    private static final String TWO = """
            <plan>
            <week capacity='8' />
            </plan>""";

    @Test
    void ignore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        var diff = new DetailedDiff(new Diff(ONE, TWO));
        assertThat(diff.similar()).isTrue();
    }

    @Test
    void notIgnore() throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(false);
        var diff = new DetailedDiff(new Diff(ONE, TWO));
        assertThat(diff.similar()).isFalse();
    }
}