package lucene.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.MockAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;

import java.io.IOException;

/**
 * Example source: https://github.com/apache/lucene-solr/blob/master/lucene/core/src/test/org/apache/lucene/TestDemo.java
 */
public class TestDemo extends LuceneTestCase {

    public void testDemo() throws IOException {
        Analyzer analyzer = new MockAnalyzer(random());

        // Store the index in memory:
        Directory directory = newDirectory();
        // To store an index on disk, use this instead:
        // Directory directory = FSDirectory.open(new File("/tmp/testindex"));
        RandomIndexWriter iwriter = new RandomIndexWriter(random(), directory, analyzer);
        Document doc = new Document();
        String longTerm = "longtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongterm";
        String text = "This is the text to be indexed. " + longTerm;
        doc.add(newTextField("fieldname", text, Field.Store.YES));
        iwriter.addDocument(doc);
        iwriter.close();

        // Now search the index:
        IndexReader ireader = DirectoryReader.open(directory); // read-only=true
        IndexSearcher isearcher = newSearcher(ireader);

        assertEquals(1, isearcher.search(new TermQuery(new Term("fieldname", longTerm)), 1).totalHits);
        Query query = new TermQuery(new Term("fieldname", "text"));
        TopDocs hits = isearcher.search(query, 1);
        assertEquals(1, hits.totalHits);
        // Iterate through the results:
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
            assertEquals(text, hitDoc.get("fieldname"));
        }

        // Test simple phrase query
        PhraseQuery phraseQuery = new PhraseQuery("fieldname", "to", "be");
        assertEquals(1, isearcher.search(phraseQuery, 1).totalHits);

        ireader.close();
        directory.close();
        analyzer.close();
    }
}
