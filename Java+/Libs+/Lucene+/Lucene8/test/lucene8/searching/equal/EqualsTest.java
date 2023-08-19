package lucene8.searching.equal;

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

class EqualsTest {

    @Test
    void document() {
        var doc1 = new Document();
        doc1.add(new TextField("textField", "John drinks water.", Field.Store.YES));
        var doc2 = new Document();
        doc2.add(new TextField("textField", "John drinks water.", Field.Store.YES));
        assertThat(doc1).isNotEqualTo(doc2);
    }

    @Test
    void field() {
        var textField1 = new TextField("textField", "John drinks water.", Field.Store.YES);
        var textField2 = new TextField("textField", "John drinks water.", Field.Store.YES);
        assertThat(textField1).isNotEqualTo(textField2);
    }

    @Test
    void directory() throws IOException {
        try (var directory1 = new ByteBuffersDirectory();
             var directory2 = new ByteBuffersDirectory()) {
            assertThat(directory1).isNotEqualTo(directory2);
        }
    }

    @Test
    void analyzer() {
        try (var analyzer1 = new StandardAnalyzer();
             var analyzer2 = new StandardAnalyzer()) {
            assertThat(analyzer1).isNotEqualTo(analyzer2);
        }
    }

    @Test
    void term() {
        var term1 = new Term("field1", "lucene");
        var term2 = new Term("field1", "lucene");
        assertThat(term1).isEqualTo(term2);
    }

    @Test
    void query() {
        var query1 = new TermQuery(new Term("field1", "lucene"));
        var query2 = new TermQuery(new Term("field1", "lucene"));
        assertThat(query1).isEqualTo(query2);
    }

    @Test
    void topDocs() throws IOException {
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
                var topDocs1 = searcher.search(query, 5);
                var topDocs2 = searcher.search(query, 5);
                assertThat(topDocs1).isNotEqualTo(topDocs2);
            }
        }
    }

    @Test
    void scoreDoc() throws IOException {
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
                var scoreDoc1 = searcher.search(query, 5).scoreDocs[0];
                var scoreDoc2 = searcher.search(query, 5).scoreDocs[0];
                assertThat(scoreDoc1).isNotEqualTo(scoreDoc2);
            }
        }
    }
}
