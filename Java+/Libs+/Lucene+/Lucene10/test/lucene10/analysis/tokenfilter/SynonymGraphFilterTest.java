package lucene10.analysis.tokenfilter;

import lucene10.IndexAssistant;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class SynonymGraphFilterTest {
    @Test
    void synonyms() throws IOException {
        var builder = new SynonymMap.Builder();
        builder.add(new CharsRef("motorbike"), new CharsRef("motorcycle"), false);
        builder.add(new CharsRef("quick"), new CharsRef("fast"), false);
        var synonymMap = builder.build();

        var fieldName = "text";
        var doc1 = newDoc(fieldName, "Motorcycle is fast");
        var doc2 = newDoc(fieldName, "Motorbike is quick");
        try (var assistant = IndexAssistant.create().setAnalyzer(new SynonymAnalyzer(synonymMap)).addDoc(doc1, doc2)) {
            var directory = assistant.getDirectory();
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("motorcycle", "is", "fast");
        }
    }

    static class SynonymAnalyzer extends StopwordAnalyzerBase {
        private final SynonymMap synonymMap;

        SynonymAnalyzer(SynonymMap synonymMap) {
            this.synonymMap = synonymMap;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            var tokenizer = new StandardTokenizer();
            var lowerCaseFilter = new LowerCaseFilter(tokenizer);
            var synonymGraphFilter = new SynonymGraphFilter(lowerCaseFilter, synonymMap, true);
            return new TokenStreamComponents(tokenizer, synonymGraphFilter);
        }
    }
}
