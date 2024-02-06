package gptui.ui.model.question.question;

import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import jakarta.inject.Singleton;

import static com.vladsch.flexmark.parser.Parser.EXTENSIONS;
import static java.util.List.of;

@Singleton
class FormatConverter {
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
        var visitor = new ExpandNestedMarkDownFencedBlocks(parser);
        visitor.visit(mdDoc);
        return renderer.render(mdDoc);
    }

    private static class ExpandNestedMarkDownFencedBlocks {
        private final Parser parser;
        private final NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(FencedCodeBlock.class, this::visitFencedCodeBlock)
        );

        private ExpandNestedMarkDownFencedBlocks(Parser parser) {
            this.parser = parser;
        }

        public void visit(Node node) {
            visitor.visit(node);
        }

        private void visitFencedCodeBlock(FencedCodeBlock fencedCodeBlock) {
            var info = fencedCodeBlock.getInfo();
            if (info != null && "markdown".equalsIgnoreCase(info.toString())) {
                var mdSeq = fencedCodeBlock.getContentChars();
                var replacement = parser.parse(mdSeq);
                fencedCodeBlock.insertAfter(replacement);
                fencedCodeBlock.unlink();
            }
        }
    }
}
