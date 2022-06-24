package parse;

import org.htmlcleaner.HtmlCleaner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTest {
    @Test
    void href() {
        var html = "<html>" +
                "<body>" +
                "<a href='next_page.html'/>" +
                "</body>" +
                "</html>";

        var cleaner = new HtmlCleaner();
        var htmlNode = cleaner.clean(html);
        var aTag = htmlNode.getElementListByName("a", true).get(0);
        var hrefAttr = aTag.getAttributeByName("href");
        assertThat(hrefAttr).isEqualTo("next_page.html");
    }
}
