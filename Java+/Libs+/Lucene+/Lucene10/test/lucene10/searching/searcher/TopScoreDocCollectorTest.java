package lucene10.searching.searcher;

import lucene10.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollectorManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.topDocsToDocuments;
import static lucene10.TopDocsAssert.assertThat;

class TopScoreDocCollectorTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = newDoc(fieldName, "A river flows to a sea.");
        var doc2 = newDoc(fieldName, "A river flows from a mountain.");
        try (var assistant = IndexAssistant.create(doc1, doc2);
             var directory = assistant.getDirectory();
             var reader = DirectoryReader.open(directory)) {
            var searcher = new IndexSearcher(reader);
            var query = new TermQuery(new Term(fieldName, "sea"));
            var manager = new TopScoreDocCollectorManager(10, 100);
            var result = searcher.search(query, manager);
            var collector = manager.newCollector();
            var topDocs = collector.topDocs();
            var actDoc = topDocsToDocuments(topDocs, searcher);
            assertThat(actDoc).isNotEmpty().contains(doc1).doesNotContain(doc2);
        }
    }
}
