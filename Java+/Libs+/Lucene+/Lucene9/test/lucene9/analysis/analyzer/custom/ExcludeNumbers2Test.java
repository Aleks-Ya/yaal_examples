package lucene9.analysis.analyzer.custom;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.FilteringTokenFilter;
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

/**
 * Using org.apache.lucene.analysis.tokenattributes.{@link CharTermAttribute}.
 */
class ExcludeNumbers2Test {
    @Test
    void filter() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought 10 pens.", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary has 1.5 liters of water.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new ExcludeNumbersAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("John", "bought", "pens", "Mary", "has", "liters", "of", "water")
                    .doesNotContain("10", "1.5");
        }
    }

    public static class ExcludeNumbersAnalyzer extends Analyzer {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            var tokenizer = new StandardTokenizer();
            var tokenStream = new ExcludeNumericFilter(tokenizer);
            return new TokenStreamComponents(tokenizer, tokenStream);
        }
    }

    public static class ExcludeNumericFilter extends FilteringTokenFilter {
        private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

        public ExcludeNumericFilter(TokenStream in) {
            super(in);
        }

        @Override
        protected boolean accept() {
            return !termAtt.toString().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        }
    }
}
