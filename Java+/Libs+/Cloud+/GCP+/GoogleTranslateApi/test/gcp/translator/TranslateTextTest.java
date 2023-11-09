package gcp.translator;

import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.Translation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TranslateTextTest extends BaseRemoteTest {
    @Test
    void translate() {
        var request = TranslateTextRequest.newBuilder()
                .setParent(parent)
                .setMimeType("text/plain")
                .setTargetLanguageCode("en")
                .addContents("кот")
                .build();
        var response = client.translateText(request);
        var translations = response.getTranslationsList();
        assertThat(translations).isNotEmpty().map(Translation::getTranslatedText).contains("cat");
    }
}
