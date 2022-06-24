package parse;

import org.htmlcleaner.HtmlCleaner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindAllTagsTest {
    @Test
    void getElementListByName() {
        var html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>Parsed HTML.</p>" +
                "<div>Part 1</div>" +
                "<div><p>nested</p></div>" +
                "</body>" +
                "</html>";

        var cleaner = new HtmlCleaner();
        var node = cleaner.clean(html);
        var pTags = node.getElementListByName("p", true);
        assertThat(pTags).hasSize(2);
        assertThat(pTags.get(0).getText()).hasToString("Parsed HTML.");
        assertThat(pTags.get(1).getText()).hasToString("nested");

    }
}
