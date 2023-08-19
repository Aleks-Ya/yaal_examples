package lucene9.indexing.reader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GetTermFrequencyTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John eats a cake.", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary eats a mango.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            var tfMap = new HashMap<String, Long>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            tfMap.put(term.utf8ToString(), termsEnum.totalTermFreq());
                        }
                    }
                }
            }
            assertThat(tfMap).containsExactlyInAnyOrderEntriesOf(Map.of(
                    "john", 1L,
                    "eats", 2L,
                    "mango", 1L,
                    "mary", 1L,
                    "cake", 1L,
                    "a", 2L
            ));
        }
    }
}
