package parse;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FindAllTagsTest {
    @Test
    public void getElementListByName() {
        String html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>Parsed HTML.</p>" +
                "<div>Part 1</div>" +
                "<div><p>nested</p></div>" +
                "</body>" +
                "</html>";

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(html);
        List<? extends TagNode> pTags = node.getElementListByName("p", true);
        assertThat(pTags, hasSize(2));
        assertThat(pTags.get(0).getText(), hasToString(equalTo("Parsed HTML.")));
        assertThat(pTags.get(1).getText(), hasToString(equalTo("nested")));

    }
}
