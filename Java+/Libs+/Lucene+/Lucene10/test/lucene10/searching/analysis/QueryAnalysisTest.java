package lucene10.searching.analysis;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.SearchHelper.topDocsToDocuments;
import static lucene10.TopDocsAssert.assertThat;
import static org.apache.lucene.document.Field.Store.YES;

/**
 * Analyzing query in the same way as documents where analyzed during indexing.
 */
class QueryAnalysisTest {
    @Test
    void test() throws IOException, ParseException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought a pen.", YES));
        var doc2 = new Document();
        doc2.add(new TextField(fieldName, "Mary broke 5 pens.", YES));

        try (var directory = new ByteBuffersDirectory()) {
            try (var analyzer = new EnglishAnalyzer()) {
                var config = new IndexWriterConfig(analyzer);
                try (var writer = new IndexWriter(directory, config)) {
                    writer.addDocument(doc1);
                    writer.addDocument(doc2);
                }
            }
            try (var reader = DirectoryReader.open(directory)) {
                var searcher = new IndexSearcher(reader);
                var analyzer = new EnglishAnalyzer();
                var parser = new QueryParser(fieldName, analyzer);
                var query = parser.parse("Pens");
                var topDocs = searcher.search(query, 10);
                var actDoc = topDocsToDocuments(topDocs, searcher);
                assertThat(actDoc).isNotEmpty().contains(doc1, doc2);
            }
        }
    }
}
