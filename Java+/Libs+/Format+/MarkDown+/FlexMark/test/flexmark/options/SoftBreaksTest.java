package flexmark.options;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoftBreaksTest {
    @Test
    void softBreaks() {
        var options = new MutableDataSet();
        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
        var parser = Parser.builder(options).build();
        var renderer = HtmlRenderer.builder(options).build();
        var document = parser.parse("This is *Sparta*");
        var html = renderer.render(document);
        assertThat(html).isEqualTo("<p>This is <em>Sparta</em></p>\n");
    }
}