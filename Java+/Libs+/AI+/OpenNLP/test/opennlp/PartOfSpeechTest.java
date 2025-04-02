package opennlp;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PartOfSpeechTest {
    @Test
    void pos() throws IOException {
        var modelFile = ModelDownloader.downloadPosModel();
        try (var modelIn = new FileInputStream(modelFile)) {
            var model = new POSModel(modelIn);
            var tagger = new POSTaggerME(model);
            var sent = new String[]{"Most", "large", "cities", "in", "the", "US", "had", "morning", "and", "afternoon", "newspapers", "."};
            var tags = tagger.tag(sent);
            assertThat(tags).contains("ADV", "ADJ", "NOUN", "ADP", "DET", "PROPN", "AUX", "NOUN", "CCONJ", "NOUN", "NOUN", "PUNCT");
        }
    }

}
