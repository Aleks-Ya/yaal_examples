package urlbuilder;

import io.mikael.urlbuilder.UrlBuilder;
import io.mikael.urlbuilder.util.UrlParameterMultimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlBuilderTest {
    @Test
    void create() {
        var baseUrl = "https://example.com";
        var path = "resources/file name with spaces/subfolder";
        var parameters = UrlParameterMultimap.newMultimap()
                .add("city name", "London")
                .add("lang", "EN");
        var encodedUrl = UrlBuilder.fromString(baseUrl)
                .withPath(path)
                .withParameters(parameters)
                .toString();
        assertThat(encodedUrl).isEqualTo(
                "https://example.com/resources/file%20name%20with%20spaces/subfolder?city%20name=London&lang=EN");
    }
}
