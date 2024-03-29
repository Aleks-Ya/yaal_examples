package lucene9.searching.query;

import lucene9.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.IndexAssistant.newDoc;
import static lucene9.SearchHelper.topDocsToDocuments;
import static lucene9.TopDocsAssert.assertThat;

class TermQueryTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        var doc1 = newDoc(fieldName, "User uses Lucene often.");
        var doc2 = newDoc(fieldName, "Flowers bloom in the summer.");
        try (var assistant = IndexAssistant.create(doc1, doc2)) {
            var directory = assistant.getDirectory();
            try (var reader = DirectoryReader.open(directory)) {
                var searcher = new IndexSearcher(reader);
                var query = new TermQuery(new Term(fieldName, "lucene"));
                var topDocs = searcher.search(query, 1);
                var actDoc = topDocsToDocuments(topDocs, searcher);
                assertThat(actDoc).isNotEmpty().contains(doc1).doesNotContain(doc2);
            }
        }
    }
}
