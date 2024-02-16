package gptui.model.question.question;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatConverterTest {
    private final FormatConverter formatConverter = new FormatConverter();

    @Test
    void markdownToHtml() {
        var md = """
                # Header 1
                Bold text: *bold*
                """;
        var html = formatConverter.markdownToHtml(md);
        assertThat(html).isEqualTo("""
                <h1>Header 1</h1>
                <p>Bold text: <em>bold</em></p>
                """);
    }

    @Test
    void tables() {
        var md = """
                Table 1
                                
                | AA | BB |
                |----|----|
                | 11 | 22 |""";
        var html = formatConverter.markdownToHtml(md);
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

    @Test
    void expandNestedMarkDownBlocks() {
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
        var html = formatConverter.markdownToHtml(md);
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
}