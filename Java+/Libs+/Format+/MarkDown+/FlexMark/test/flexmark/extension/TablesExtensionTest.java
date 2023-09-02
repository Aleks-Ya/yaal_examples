package flexmark.extension;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TablesExtensionTest {
    @Test
    void extension() {
        var options = new MutableDataSet().set(Parser.EXTENSIONS, List.of(TablesExtension.create()));
        var parser = Parser.builder(options).build();
        var renderer = HtmlRenderer.builder(options).build();
        var text = """
                Table 1
                                
                | AA | BB |
                |----|----|
                | 11 | 22 |""";
        var document = parser.parse(text);
        var html = renderer.render(document);
        assertThat(html).isEqualTo("""
                <p>Table 1</p>
                <table>
                <thead>
                <tr><th>AA</th><th>BB</th></tr>
                </thead>
                <tbody>
                <tr><td>11</td><td>22</td></tr>
                </tbody>
                </table>
                  """);
    }
}