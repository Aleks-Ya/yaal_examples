package lucene10.searching.query;

import lucene10.IndexAssistant;
import lucene10.TopDocsAssert;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.topDocsToDocuments;

class BooleanQueryTest {
    @Test
    void test() throws IOException {
        var fieldName = "fieldname";
        var doc1 = newDoc(fieldName, "A river flows to a sea.");
        var doc2 = newDoc(fieldName, "A river flows to an ocean.");
        try (var assistant = IndexAssistant.create(doc1, doc2);
             var directory = assistant.getDirectory();
             var reader = DirectoryReader.open(directory)) {
            var searcher = new IndexSearcher(reader);
            var query1 = new TermQuery(new Term(fieldName, "river"));
            var query2 = new TermQuery(new Term(fieldName, "sea"));
            var query = new BooleanQuery.Builder()
                    .add(query1, BooleanClause.Occur.MUST)
                    .add(query2, BooleanClause.Occur.MUST)
                    .build();
            var topDocs = searcher.search(query, 1);
            var actDoc = topDocsToDocuments(topDocs, searcher);
            TopDocsAssert.assertThat(actDoc).isNotEmpty().contains(doc1).doesNotContain(doc2);
        }
    }
}
