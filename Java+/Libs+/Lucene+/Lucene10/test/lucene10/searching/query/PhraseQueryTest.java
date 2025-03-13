package lucene10.searching.query;

import lucene10.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.topDocsToDocuments;
import static lucene10.TopDocsAssert.assertThat;

class PhraseQueryTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = newDoc(fieldName, "A river flows to a sea.");
        var doc2 = newDoc(fieldName, "A river flows from a sea.");
        var doc3 = newDoc(fieldName, "A stream flows to a ocean");
        var doc4 = newDoc(fieldName, "A sea flows to an ocean");
        try (var assistant = IndexAssistant.create(doc1, doc2, doc3, doc4)) {
            var directory = assistant.getDirectory();
            try (var reader = DirectoryReader.open(directory)) {
                var searcher = new IndexSearcher(reader);
                var query = new PhraseQuery.Builder()
                        .add(new Term(fieldName, "flows"), 1)
                        .add(new Term(fieldName, "sea"), 2)
                        .setSlop(5)
                        .build();
                var topDocs = searcher.search(query, 10);
                var actDoc = topDocsToDocuments(topDocs, searcher);
                assertThat(actDoc).isNotEmpty().contains(doc1, doc2, doc4).doesNotContain(doc3);
            }
        }
    }
}
