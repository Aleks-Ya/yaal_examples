package parse;

import org.hamcrest.Matchers;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.Test;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class XPathTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    public void correctHtml() throws XPatherException {
        String html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>Parsed HTML into a doc.</p>" +
                "<div>Part 1</div>" +
                "<div>Part 2</div>" +
                "</body>" +
                "</html>";
        TagNode htmlTag = cleaner.clean(html);
        Object[] tags = htmlTag.evaluateXPath("//body//p");
        assertThat(tags, Matchers.arrayWithSize(1));
        TagNode pTag = (TagNode) tags[0];
        assertThat(pTag.getName(), equalTo("p"));
        assertThat(pTag.getText(), hasToString(equalTo("Parsed HTML into a doc.")));
    }
}
