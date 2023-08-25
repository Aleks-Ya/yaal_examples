package lucene9.analysis.tokenfilter;

import lucene9.IndexAssistant;
import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.IndexAssistant.newDoc;
import static lucene9.SearchHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class CustomTokenFilterTest {
    @Test
    void custom() {
        var fieldName = "text";
        var doc1 = newDoc(fieldName, "John is 30");
        var doc2 = newDoc(fieldName, "Mary is 25");
        try (var assistant = IndexAssistant.create().setAnalyzer(new UpperCaseAnalyzer()).addDoc(doc1, doc2)) {
            var directory = assistant.getDirectory();
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("JOHN", "IS", "30", "25", "MARY");

        }
    }

    static class UpperCaseAnalyzer extends StopwordAnalyzerBase {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            var tokenizer = new StandardTokenizer();
            var tokenStream = new UpperCaseFilter(tokenizer);
            return new TokenStreamComponents(tokenizer, tokenStream);
        }
    }

    static class UpperCaseFilter extends TokenFilter {
        private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

        public UpperCaseFilter(TokenStream in) {
            super(in);
        }

        @Override
        public final boolean incrementToken() throws IOException {
            if (input.incrementToken()) {
                CharacterUtils.toUpperCase(termAtt.buffer(), 0, termAtt.length());
                return true;
            } else {
                return false;
            }
        }
    }
}
