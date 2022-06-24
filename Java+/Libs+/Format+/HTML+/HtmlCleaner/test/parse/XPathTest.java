package parse;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XPathTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    void correctHtml() throws XPatherException {
        var html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>Parsed HTML into a doc.</p>" +
                "<div>Part 1</div>" +
                "<div>Part 2</div>" +
                "</body>" +
                "</html>";
        var htmlTag = cleaner.clean(html);
        var tags = htmlTag.evaluateXPath("//body//p");
        assertThat(tags).hasSize(1);
        var pTag = (TagNode) tags[0];
        assertThat(pTag.getName()).isEqualTo("p");
        assertThat(pTag.getText()).hasToString("Parsed HTML into a doc.");
    }
}
