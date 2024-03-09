package gptui.model.search;

import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.StorageModel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.lucene.document.Field.Store.YES;
import static org.apache.lucene.search.BooleanClause.Occur.SHOULD;

@Singleton
class HistorySearchModelImpl implements HistorySearchModel {
    private static final String INTERACTION_ID_FIELD = "interactionId";
    private static final String THEME_FIELD = "theme";
    private static final String QUESTION_FIELD = "question";
    private final Directory directory = new ByteBuffersDirectory();
    private final IndexWriter writer;
    private boolean indexUpdated = true;
    private IndexSearcher searcher;
    @Inject
    private StorageModel storageModel;

    public HistorySearchModelImpl() {
        try {
            var analyzer = new EnglishAnalyzer();
            var conf = new IndexWriterConfig(analyzer);
            writer = new IndexWriter(directory, conf);
            writer.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document interactionToDocument(Interaction interaction) {
        var doc = new Document();
        doc.add(new LongField(INTERACTION_ID_FIELD, interaction.id().id(), YES));
        doc.add(new TextField(THEME_FIELD, storageModel.getTheme(interaction.themeId()).title(), YES));
        doc.add(new TextField(QUESTION_FIELD, interaction.question(), YES));
        return doc;
    }

    @Override
    public List<InteractionId> search(String text) {
        try {
            if (indexUpdated) {
                var reader = DirectoryReader.open(directory);
                searcher = new IndexSearcher(reader);
                indexUpdated = false;
            }
            var analyzer = new EnglishAnalyzer();
            var themeParser = new QueryParser(THEME_FIELD, analyzer);
            var questionParser = new QueryParser(QUESTION_FIELD, analyzer);

            var themeQuery = themeParser.parse(text);
            var questionQuery = questionParser.parse(text);

            var query = new BooleanQuery.Builder()
                    .add(themeQuery, SHOULD)
                    .add(questionQuery, SHOULD)
                    .setMinimumNumberShouldMatch(1)
                    .build();
            var topDocs = searcher.search(query, 10);
            var result = new ArrayList<InteractionId>();
            for (var scoreDoc : topDocs.scoreDocs) {
                var document = searcher.storedFields().document(scoreDoc.doc);
                var interactionIdField = document.getField(INTERACTION_ID_FIELD);
                var interactionId = interactionIdField.numericValue();
                result.add(new InteractionId(interactionId.longValue()));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void indexDocuments(List<Interaction> interactions) {
        try {
            for (var interaction : interactions) {
                writer.addDocument(interactionToDocument(interaction));
            }
            writer.commit();
            indexUpdated = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void indexDocument(Interaction interaction) {
        try {
            writer.addDocument(interactionToDocument(interaction));
            writer.commit();
            indexUpdated = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
