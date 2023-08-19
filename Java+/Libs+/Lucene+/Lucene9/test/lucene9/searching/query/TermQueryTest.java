package lucene9.searching.query;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class TermQueryTest {
    @Test
    void test() throws IOException {
        var fieldName = "fieldname";
        var text = "Some of the query types provided by Elasticsearch support Apache Lucene query parser syntax.";
        var doc = new Document();
        doc.add(new TextField(fieldName, text, Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc);
            }
            try (var reader = DirectoryReader.open(directory)) {
                var searcher = new IndexSearcher(reader);
                var query = new TermQuery(new Term(fieldName, "lucene"));
                var hits = searcher.search(query, 1);
                assertThat(hits.totalHits.value).isEqualTo(1);
                var docId = hits.scoreDocs[0].doc;
                var actDoc = searcher.storedFields().document(docId);
                assertThat(actDoc).hasToString(doc.toString());
            }
        }
    }
}
