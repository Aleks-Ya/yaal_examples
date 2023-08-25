package lucene9.analysis.analyzer;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.SearchHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class EnglishAnalyzerTest {
    @Test
    void standard() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought a pen.", Field.Store.YES));
        var doc2 = new Document();
        doc1.add(new TextField(fieldName, "Mary broke 5 pens.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new EnglishAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("john", "bought", "pen", "mari", "broke", "5");
        }
    }
}
