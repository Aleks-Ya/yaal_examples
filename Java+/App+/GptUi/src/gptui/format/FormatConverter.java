package gptui.format;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

import static com.vladsch.flexmark.parser.Parser.EXTENSIONS;
import static java.util.List.of;

public class FormatConverter {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public FormatConverter() {
        var options = new MutableDataSet().set(EXTENSIONS, of(TablesExtension.create()));
        parser = Parser.builder(new MutableDataSet().set(EXTENSIONS, of(TablesExtension.create())))
                .build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public String markdownToHtml(String markdown) {
        var mdDoc = parser.parse(markdown);
        return renderer.render(mdDoc);
    }
}
