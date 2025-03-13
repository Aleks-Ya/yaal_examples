package lucene10.searching.score;

import lucene10.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene10.IndexAssistant.newDoc;
import static lucene10.SearchHelper.topDocsToIdToDocumentMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class ExplainTest {
    @Test
    void explain() throws IOException {
        var fieldName = "text";
        var doc0 = newDoc(fieldName, "User uses Lucene often.");
        var doc1 = newDoc(fieldName, "Flowers bloom in the summer.");
        var doc2 = newDoc(fieldName, "Lucene evolves.");
        var docId0 = 0;
        var docId1 = 1;
        var docId2 = 2;
        try (var assistant = IndexAssistant.create(doc0, doc1, doc2)) {
            var directory = assistant.getDirectory();
            try (var reader = DirectoryReader.open(directory)) {
                var searcher = new IndexSearcher(reader);
                var query = new TermQuery(new Term(fieldName, "lucene"));
                var topDocs = searcher.search(query, 3);
                var actDocMap = topDocsToIdToDocumentMap(topDocs, searcher);
                assertThat(actDocMap).hasSize(2)
                        .containsKeys(docId0, docId2)
                        .doesNotContainKey(docId1);

                var explanation = searcher.explain(query, docId0);
                assertThat(explanation.getDescription()).isEqualTo("weight(text:lucene in 0) [BM25Similarity], result of:");
                assertThat(explanation.getValue().doubleValue()).isCloseTo(0.205D, offset(0.001));
            }
        }
    }
}
