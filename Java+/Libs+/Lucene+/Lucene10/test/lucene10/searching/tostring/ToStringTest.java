package lucene10.searching.tostring;

import lucene10.IndexAssistant;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static org.assertj.core.api.Assertions.assertThat;

class ToStringTest {
    @Test
    void document() {
        var doc = new Document();
        doc.add(new TextField("textField", "John drinks water.", Field.Store.YES));
        doc.add(new IntField("intField", 42, Field.Store.YES));
        assertThat(doc).hasToString(
                "Document<stored,indexed,tokenized<textField:John drinks water.> IntField <intField:42>>");
    }

    @Test
    void field() {
        assertThat(new TextField("textField", "John drinks water.", Field.Store.YES)).hasToString(
                "stored,indexed,tokenized<textField:John drinks water.>");
        assertThat(new IntField("intField", 42, Field.Store.YES)).hasToString(
                "IntField <intField:42>");
    }

    @Test
    void directory() throws IOException {
        try (var directory = new ByteBuffersDirectory()) {
            assertThat(directory.toString()).containsPattern(
                    "ByteBuffersDirectory@\\w+ lockFactory=org.apache.lucene.store.SingleInstanceLockFactory@\\w+");
        }
    }

    @Test
    void analyzer() {
        try (var analyzer = new StandardAnalyzer()) {
            assertThat(analyzer.toString())
                    .containsPattern("org.apache.lucene.analysis.standard.StandardAnalyzer@\\w+");
        }
    }

    @Test
    void term() {
        var term = new Term("field1", "lucene");
        assertThat(term).hasToString("field1:lucene");
    }

    @Test
    void query() {
        var query = new TermQuery(new Term("field1", "lucene"));
        assertThat(query).hasToString("field1:lucene");
    }


    @Test
    void topDocs() throws IOException {
        var fieldName = "text";
        var doc = newDoc(fieldName, "Some of the query types provided by Elasticsearch support Apache Lucene query parser syntax.");
        try (var assistant = IndexAssistant.create(doc);
             var directory = assistant.getDirectory();
             var reader = DirectoryReader.open(directory)) {
            var searcher = new IndexSearcher(reader);
            var query = new TermQuery(new Term(fieldName, "lucene"));
            var topDocs = searcher.search(query, 5);
            assertThat(topDocs.toString()).containsPattern("org.apache.lucene.search.TopDocs@\\w+");
        }
    }

    @Test
    void scoreDoc() throws IOException {
        var fieldName = "text";
        var doc = newDoc(fieldName, "Some of the query types provided by Elasticsearch support Apache Lucene query parser syntax.");
        try (var assistant = IndexAssistant.create(doc);
             var directory = assistant.getDirectory();
             var reader = DirectoryReader.open(directory)) {
            var searcher = new IndexSearcher(reader);
            var query = new TermQuery(new Term(fieldName, "lucene"));
            var topDocs = searcher.search(query, 5);
            var scoreDoc = topDocs.scoreDocs[0];
            assertThat(scoreDoc).hasToString("doc=0 score=0.13076457 shardIndex=-1");
        }
    }
}
