package lucene9.analysis.tokenfilter;

import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.LuceneHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class CustomTokenFilterTest {
    @Test
    void custom() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John is 30", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary is 25", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new UpperCaseAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
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
