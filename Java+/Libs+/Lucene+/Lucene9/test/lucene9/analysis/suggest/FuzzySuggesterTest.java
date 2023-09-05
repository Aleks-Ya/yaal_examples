package lucene9.analysis.suggest;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToPath;

class FuzzySuggesterTest {
    @Test
    void suggest() throws IOException {
        try (var tempDir = new ByteBuffersDirectory();
             var analyzer = new StandardAnalyzer()) {

            var file = resourceToPath(getClass(), "FuzzySuggesterTest_PlainTextDictionary.txt");
            var dictionary = new PlainTextDictionary(file);

            var tmpFilenamePrefix = "tmp";

            var suggester = new FuzzySuggester(tempDir, tmpFilenamePrefix, analyzer);
            suggester.build(dictionary);
            List<Lookup.LookupResult> lookup = suggester.lookup("raod", false, 5);
            assertThat(lookup).extracting("key").contains("road");
        }
    }
}
