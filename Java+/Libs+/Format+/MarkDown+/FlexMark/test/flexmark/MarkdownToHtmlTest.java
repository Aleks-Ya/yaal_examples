package flexmark;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MarkdownToHtmlTest {
    @Test
    void convert() {
        var parser = Parser.builder().build();
        var renderer = HtmlRenderer.builder().build();
        var document = parser.parse("This is *Sparta*");
        var html = renderer.render(document);
        assertThat(html).isEqualTo("<p>This is <em>Sparta</em></p>\n");
    }
}