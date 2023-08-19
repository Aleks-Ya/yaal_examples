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
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class IterateAllTermsTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John drinks water.", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary eats a cake.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            var actTermList = new ArrayList<String>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            actTermList.add(term.utf8ToString());
                        }
                    }
                }
            }
            assertThat(actTermList).containsExactlyInAnyOrder(
                    "john", "drinks", "water", "mary", "eats", "a", "cake");
        }
    }
}
