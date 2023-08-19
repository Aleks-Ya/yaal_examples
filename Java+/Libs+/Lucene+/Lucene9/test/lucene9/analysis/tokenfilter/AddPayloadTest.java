package lucene9.analysis.tokenfilter;

import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static lucene9.LuceneHelper.directoryToTermList;
import static lucene9.LuceneHelper.directoryToTermToPayloadsMap;
import static org.assertj.core.api.Assertions.assertThat;

class AddPayloadTest {
    @Test
    void payload() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John is 30", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary is 25", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new PayloadAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("John", "is", "30", "25", "Mary");

            assertThat(directoryToTermToPayloadsMap(directory, fieldName)).containsExactlyInAnyOrderEntriesOf(Map.of(
                    "25", List.of("Upper=25, Counter=6"),
                    "30", List.of("Upper=30, Counter=3"),
                    "John", List.of("Upper=JOHN, Counter=1"),
                    "Mary", List.of("Upper=MARY, Counter=4"),
                    "is", List.of("Upper=IS, Counter=2", "Upper=IS, Counter=5")));
        }
    }

    static class PayloadAnalyzer extends StopwordAnalyzerBase {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            var tokenizer = new StandardTokenizer();
            var tokenStream = new PayloadFilter(tokenizer);
            return new TokenStreamComponents(tokenizer, tokenStream);
        }
    }

    static class PayloadFilter extends TokenFilter {
        private static final AtomicInteger counter = new AtomicInteger();
        private final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);
        private final PayloadAttribute payloadAttr = addAttribute(PayloadAttribute.class);

        public PayloadFilter(TokenStream in) {
            super(in);
        }

        @Override
        public final boolean incrementToken() throws IOException {
            if (input.incrementToken()) {
                var attrValue = termAttr.toString();
                var payloadBytes = String.format("Upper=%s, Counter=%d", attrValue.toUpperCase(), counter.incrementAndGet());
                payloadAttr.setPayload(new BytesRef(payloadBytes));
                return true;
            } else {
                return false;
            }
        }
    }
}
