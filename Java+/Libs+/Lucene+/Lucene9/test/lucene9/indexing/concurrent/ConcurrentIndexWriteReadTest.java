package lucene9.indexing.concurrent;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static lucene9.SearchHelper.topDocsToDocuments;
import static lucene9.TopDocsAssert.assertThat;
import static org.apache.lucene.document.Field.Store.YES;

/**
 * Simultaneously write and read the same directory.
 */
class ConcurrentIndexWriteReadTest {
    private static final String FIELD_NAME = "text";

    @Test
    void test() throws IOException {
        var doc1 = new Document();
        doc1.add(new TextField(FIELD_NAME, "John bought a pen.", YES));
        var doc2 = new Document();
        doc2.add(new TextField(FIELD_NAME, "Mary broke the pen.", YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer();
             var writer = new IndexWriter(directory, new IndexWriterConfig(analyzer))) {
            writer.commit();
            assertThat(searchNewReader(directory)).isEmpty();
            writer.addDocument(doc1);
            assertThat(searchNewReader(directory)).isEmpty();
            writer.commit();
            assertThat(searchNewReader(directory)).isNotEmpty().contains(doc1).doesNotContain(doc2);
            try (var reader = DirectoryReader.open(directory)) {
                writer.addDocument(doc2);
                assertThat(searchExistingReader(reader)).isNotEmpty().contains(doc1).doesNotContain(doc2);
                writer.commit();
                assertThat(searchExistingReader(reader)).isNotEmpty().contains(doc1).doesNotContain(doc2);
                assertThat(searchNewReader(directory)).isNotEmpty().contains(doc1, doc2);
            }
        }
    }

    private List<Document> searchNewReader(Directory directory) throws IOException {
        try (var reader = DirectoryReader.open(directory)) {
            return searchExistingReader(reader);
        }
    }

    private static List<Document> searchExistingReader(DirectoryReader reader) throws IOException {
        var searcher = new IndexSearcher(reader);
        var query = new TermQuery(new Term(FIELD_NAME, "pen"));
        var topDocs = searcher.search(query, 10);
        return topDocsToDocuments(topDocs, searcher);
    }
}
