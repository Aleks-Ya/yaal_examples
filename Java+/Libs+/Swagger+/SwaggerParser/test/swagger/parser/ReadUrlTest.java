package swagger.parser;

import io.swagger.parser.OpenAPIParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReadUrlTest {
    @Test
    void read() {
        var result = new OpenAPIParser()
                .readLocation("https://petstore3.swagger.io/api/v3/openapi.json", null, null);
        assertThat(result.getMessages()).isEmpty();
        var openAPI = result.getOpenAPI();
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getPaths().get("/pet/findByStatus")).isNotNull();
    }
}
