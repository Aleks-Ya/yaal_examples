package lucene9.searching.suggest;

import lucene9.IndexAssistant;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static lucene9.IndexAssistant.newDoc;
import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToPath;

class FuzzySuggesterTest {
    @Test
    void plainTextDictionary() throws IOException {
        try (var tmpSuggesterDirectory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {

            var dictionaryFile = resourceToPath(getClass(), "FuzzySuggesterTest_PlainTextDictionary.txt");
            var dictionary = new PlainTextDictionary(dictionaryFile);

            var tmpFilenamePrefix = "tmp";

            var suggester = new FuzzySuggester(tmpSuggesterDirectory, tmpFilenamePrefix, analyzer);
            suggester.build(dictionary);
            var lookup = suggester.lookup("raod", false, 5);
            assertThat(lookup).extracting("key").contains("road");
        }
    }

    @Test
    void luceneDictionary() throws IOException {
        var fieldName = "fieldname";
        var doc1 = newDoc(fieldName, "A river flows to a sea.");
        var doc2 = newDoc(fieldName, "A river flows to an ocean.");
        try (var assistant = IndexAssistant.create(doc1, doc2);
             var directory = assistant.getDirectory();
             var reader = DirectoryReader.open(directory);
             var tmpSuggesterDirectory = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {
            var dictionary = new LuceneDictionary(reader, fieldName);
            var tmpFilenamePrefix = "tmp";
            var suggester = new FuzzySuggester(tmpSuggesterDirectory, tmpFilenamePrefix, analyzer);
            suggester.build(dictionary);
            var lookup = suggester.lookup("fliws", false, 5);
            assertThat(lookup).extracting("key").contains("flows");
        }
    }
}
