package parse;

import org.htmlcleaner.HtmlCleaner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTest {
    @Test
    void correctHtml() {
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
        verify(html);
    }

    @Test
    void skipHeadTagHtml() {
        var html = "<html>" +
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
        var cleaner = new HtmlCleaner();
        var node = cleaner.clean(html);
        assertThat(node.getName()).isEqualTo("html");
        assertThat(node.getChildTags()).hasSize(2);
        var headTag = node.getChildTags()[0];
        assertThat(headTag.getName()).isEqualTo("head");
        var titleTag = headTag.getChildTags()[0];
        assertThat(titleTag.getText()).hasToString("First parse");
    }
}
