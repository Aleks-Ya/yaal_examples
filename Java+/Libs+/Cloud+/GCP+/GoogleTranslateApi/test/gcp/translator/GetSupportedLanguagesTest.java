package gcp.translator;

import com.google.cloud.translate.v3.GetSupportedLanguagesRequest;
import com.google.cloud.translate.v3.SupportedLanguage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetSupportedLanguagesTest extends BaseRemoteTest {
    @Test
    void get() {
        var request = GetSupportedLanguagesRequest.newBuilder().setParent(parent).build();
        var response = client.getSupportedLanguages(request);
        var languages = response.getLanguagesList();
        assertThat(languages).isNotEmpty().map(SupportedLanguage::getLanguageCode).contains("ru", "en");
    }
}
