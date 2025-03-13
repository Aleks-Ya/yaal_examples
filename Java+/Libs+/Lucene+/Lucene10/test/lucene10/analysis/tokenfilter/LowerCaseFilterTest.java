package lucene10.analysis.tokenfilter;

import lucene10.IndexAssistant;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.junit.jupiter.api.Test;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class LowerCaseFilterTest {
    @Test
    void lowercase() {
        var fieldName = "text";
        var doc1 = newDoc(fieldName, "John is 30");
        var doc2 = newDoc(fieldName, "Mary is 25");
        try (var assistant = IndexAssistant.create().setAnalyzer(new LowerCaseStandardAnalyzer()).addDoc(doc1, doc2)) {
            var directory = assistant.getDirectory();
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("john", "is", "30", "25", "mary");
        }
    }

    static class LowerCaseStandardAnalyzer extends StopwordAnalyzerBase {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            var tokenizer = new StandardTokenizer();
            var filter = new LowerCaseFilter(tokenizer);
            return new TokenStreamComponents(tokenizer, filter);
        }
    }
}
