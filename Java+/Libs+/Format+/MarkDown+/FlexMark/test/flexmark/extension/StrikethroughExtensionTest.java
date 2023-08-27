package flexmark.extension;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StrikethroughExtensionTest {
    @Test
    void extension() {
        var options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(StrikethroughExtension.create()));
        var parser = Parser.builder(options).build();
        var renderer = HtmlRenderer.builder(options).build();
        var document = parser.parse("This is *Sparta* not ~~Greece~~");
        var html = renderer.render(document);
        assertThat(html).isEqualTo("<p>This is <em>Sparta</em> not <del>Greece</del></p>\n");
    }
}