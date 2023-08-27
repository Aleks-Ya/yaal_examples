package gptui.format;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

public class FormatConverter {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public String markdownToHtml(String markdown) {
        var mdDoc = parser.parse(markdown);
        return renderer.render(mdDoc);
    }
}
