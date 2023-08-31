package lucene9.analysis.analyzer;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class KeywordAnalyzerTest {
    @Test
    void tokenStream() throws IOException {
        try (var analyzer = new KeywordAnalyzer()) {
            var text = "Your text to analyze";
            var tokenStream = analyzer.tokenStream("fieldName", new StringReader(text));
            tokenStream.reset();
            assertThat(tokenStream.incrementToken()).isTrue();
            var token = tokenStream.getAttribute(TermToBytesRefAttribute.class);
            assertThat(token.getBytesRef().utf8ToString()).isEqualTo(text);
            assertThat(tokenStream.incrementToken()).isFalse();
            tokenStream.end();
        }
    }
}
