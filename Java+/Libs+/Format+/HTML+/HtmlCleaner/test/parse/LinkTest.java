package parse;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LinkTest {
    @Test
    public void href() {
        String html = "<html>" +
                "<body>" +
                "<a href='next_page.html'/>" +
                "</body>" +
                "</html>";

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode htmlNode = cleaner.clean(html);
        TagNode aTag = htmlNode.getElementListByName("a", true).get(0);
        String hrefAttr = aTag.getAttributeByName("href");
        assertThat(hrefAttr, equalTo("next_page.html"));
    }
}
