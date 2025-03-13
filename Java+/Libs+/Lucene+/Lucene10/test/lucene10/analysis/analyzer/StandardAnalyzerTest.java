package lucene10.analysis.analyzer;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lucene10.SearchHelper.directoryToTermList;
import static org.assertj.core.api.Assertions.assertThat;

class StandardAnalyzerTest {
    @Test
    void standard() throws IOException {
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought a pen.", Field.Store.YES));
        var doc2 = new Document();
        doc2.add(new TextField(fieldName, "Mary broke the pen.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("john", "bought", "pen", "mary", "broke", "a", "the");
        }
    }

    @Test
    void stopWords() throws IOException {
        var stopWords = List.of("a", "an", "the");
        var englishStopWords = new CharArraySet(stopWords, true);
        var fieldName = "text";
        var doc1 = new Document();
        doc1.add(new TextField(fieldName, "John bought a pen.", Field.Store.YES));
        var doc2 = new Document();
        doc2.add(new TextField(fieldName, "Mary broke the pen.", Field.Store.YES));

        try (var directory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer(englishStopWords)) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                writer.addDocument(doc1);
                writer.addDocument(doc2);
            }
            assertThat(directoryToTermList(directory, fieldName))
                    .containsExactlyInAnyOrder("john", "bought", "pen", "mary", "broke")
                    .doesNotContainAnyElementsOf(stopWords);
        }
    }

    @Test
    void analyzerOnly() throws IOException {
        var tokens = new ArrayList<String>();
        var text = "Example text_data to analyze using a StandardAnalyzer.";
        try (var analyzer = new StandardAnalyzer();
             var tokenStream = analyzer.tokenStream(null, text)) {
            var attr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                tokens.add(attr.toString());
            }
            tokenStream.end();
        }
        assertThat(tokens).containsExactly("example", "text_data", "to", "analyze", "using", "a", "standardanalyzer");
    }
}
