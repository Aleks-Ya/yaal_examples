package parse;

import org.hamcrest.Matchers;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloWorldTest {
    @Test
    public void correctHtml() {
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
        verify(html);
    }

    @Test
    public void skipHeadTagHtml() {
        String html = "<html>" +
                "<title>First parse</title>" +
                "<body>" +
                "<p>Parsed HTML into a doc.</p>" +
                "<div>Part 1</div>" +
                "<div>Part 2</div>" +
                "</body>" +
                "</html>";
        verify(html);
    }

    private void verify(String html) {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(html);
        assertThat(node.getName(), equalTo("html"));
        assertThat(node.getChildTags(), Matchers.arrayWithSize(2));
        TagNode headTag = node.getChildTags()[0];
        assertThat(headTag.getName(), equalTo("head"));
        TagNode titleTag = headTag.getChildTags()[0];
        assertThat(titleTag.getText(), hasToString(equalTo("First parse")));
    }
}
