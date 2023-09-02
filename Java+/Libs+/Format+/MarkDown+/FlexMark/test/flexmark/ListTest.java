package flexmark;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListTest {
    @Test
    void list() {
        var md = """
                Data:
                1. AAA
                2. BBB
                """;
        var parser = Parser.builder().build();
        var renderer = HtmlRenderer.builder().build();
        var document = parser.parse(md);
        var html = renderer.render(document);
        assertThat(html).isEqualTo("""
                <p>Data:</p>
                <ol>
                <li>AAA</li>
                <li>BBB</li>
                </ol>
                """);
    }
}