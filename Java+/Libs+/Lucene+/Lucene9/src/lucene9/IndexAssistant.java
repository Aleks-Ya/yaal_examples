package lucene9;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndexAssistant implements AutoCloseable {
    private final List<Document> documents = new ArrayList<>();
    private final Directory directory = new ByteBuffersDirectory();
    private final Set<Analyzer> analyzers = new HashSet<>();
    private Analyzer defaultAnalyzer = new StandardAnalyzer();

    private IndexAssistant() {
    }

    public static IndexAssistant create() {
        return new IndexAssistant();
    }

    public static IndexAssistant create(Document... docs) {
        var indexAssistant = new IndexAssistant();
        indexAssistant.addDoc(docs);
        return indexAssistant;
    }

    public IndexAssistant setAnalyzer(Analyzer analyzer) {
        defaultAnalyzer = analyzer;
        return this;
    }

    public List<Document> getAllDocuments() {
        return documents;
    }

    public Directory getDirectory() {
        return directory;
    }

    public IndexAssistant addDoc(Analyzer analyzer, String fieldName, List<String> fieldValues) {
        var docs = fieldValues.stream().map(fieldValue -> {
            var doc = new Document();
            doc.add(new TextField(fieldName, fieldValue, Field.Store.YES));
            return doc;
        }).toList();
        addDoc(analyzer, docs.toArray(Document[]::new));
        return this;
    }

    public IndexAssistant addDoc(String fieldName, List<String> fieldValues) {
        return addDoc(defaultAnalyzer, fieldName, fieldValues);
    }

    public IndexAssistant addDoc(String fieldName, String fieldValue) {
        return addDoc(defaultAnalyzer, fieldName, List.of(fieldValue));
    }

    public IndexAssistant addDoc(Document... docs) {
        return addDoc(defaultAnalyzer, docs);
    }

    public IndexAssistant addDoc(Analyzer analyzer, Document... docs) {
        analyzers.add(analyzer);
        var config = new IndexWriterConfig(analyzer);
        try (var writer = new IndexWriter(directory, config)) {
            for (var doc : docs) {
                writer.addDocument(doc);
                documents.add(doc);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public static Document newDoc(String fieldName, String fieldValue) {
        var doc = new Document();
        doc.add(new TextField(fieldName, fieldValue, Field.Store.YES));
        return doc;
    }

    @Override
    public void close() {
        try {
            getDirectory().close();
            analyzers.forEach(Analyzer::close);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
