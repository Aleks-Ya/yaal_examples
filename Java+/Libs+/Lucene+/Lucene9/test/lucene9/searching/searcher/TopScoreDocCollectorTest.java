package lucene9.searching.searcher;

import lucene9.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.IndexAssistant.newDoc;
import static lucene9.SearchHelper.topDocsToDocuments;
import static lucene9.TopDocsAssert.assertThat;

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
            var collector = TopScoreDocCollector.create(10, 100);
            searcher.search(query, collector);
            var topDocs = collector.topDocs();
            var actDoc = topDocsToDocuments(topDocs, searcher);
            assertThat(actDoc).isNotEmpty().contains(doc1).doesNotContain(doc2);
        }
    }
}
