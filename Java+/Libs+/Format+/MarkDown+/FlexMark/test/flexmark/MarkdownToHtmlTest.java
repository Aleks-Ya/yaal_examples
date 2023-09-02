package flexmark;

import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
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

    @Test
    void nestedMarkDownWithCodeTag() {
        var md = """
                Data:
                ```markdown
                1. AAA
                2. BBB
                ```
                """;
        var parser = Parser.builder().build();
        var renderer = HtmlRenderer.builder().build();
        var document = parser.parse(md);
        var html = renderer.render(document);
        assertThat(html).isEqualTo("""
                <p>Data:</p>
                <pre><code class="language-markdown">1. AAA
                2. BBB
                </code></pre>
                """);
    }

    @Test
    void nestedMarkDownWithoutCodeTag() {
        var md = """
                Data:
                ```markdown
                1. AAA
                2. BBB
                ```
                                
                Should skip:
                ```plaintext
                Hi, FlexMark
                ```
                                
                New Data:
                ```markdown
                # Head 1
                *Important*
                ```
                """;
        var parser = Parser.builder().build();
        var renderer = HtmlRenderer.builder().build();
        var document = parser.parse(md);

        var visitor = new ExpandNestedMarkDownFencedBlocks(parser);
        visitor.visit(document);
        var html = renderer.render(document);
        assertThat(html).isEqualTo("""
                <p>Data:</p>
                <ol>
                <li>AAA</li>
                <li>BBB</li>
                </ol>
                <p>Should skip:</p>
                <pre><code class="language-plaintext">Hi, FlexMark
                </code></pre>
                <p>New Data:</p>
                <h1>Head 1</h1>
                <p><em>Important</em></p>
                """);
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

    @Test
    void changeFenceCodeBlockLanguage() {
        var md = """
                Data:
                ```markdown
                1. AAA
                2. BBB
                ```
                """;
        var parser = Parser.builder().build();
        var renderer = HtmlRenderer.builder().build();
        var document = parser.parse(md);
        var child = (FencedCodeBlock) document.getChildOfType(FencedCodeBlock.class);
        if (child != null) {
            var info = child.getInfo();
            if (info != null && info.equalsIgnoreCase("markdown")) {
                child.setInfo(BasedSequence.of("plaintext"));
            }
        }
        var html = renderer.render(document);
        assertThat(html).isEqualTo("""
                <p>Data:</p>
                <pre><code class="language-plaintext">1. AAA
                2. BBB
                </code></pre>
                """);
    }
}