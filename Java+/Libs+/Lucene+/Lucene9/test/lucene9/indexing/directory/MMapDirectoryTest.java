package lucene9.indexing.directory;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.MMapDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static lucene9.LuceneHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class MMapDirectoryTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought a pen.", Field.Store.YES));
        var doc2 = new Document();
        doc2.add(new TextField(fieldName, "Mary broke the pen.", Field.Store.YES));

        var tmpDir = Files.createTempDirectory(MMapDirectoryTest.class.getSimpleName());
        try (var directory = new MMapDirectory(tmpDir);
             var analyzer = new StandardAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("john", "bought", "pen", "mary", "broke", "a", "the");
        }
    }
}
