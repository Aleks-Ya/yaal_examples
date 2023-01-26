package swagger.parser;

import io.swagger.parser.OpenAPIParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReadStringTest {
    @Test
    void read() {
        var specification = """
                openapi: 3.0.3
                info:
                  title: HomeMessanger API
                  version: 1.0.1
                paths:
                  /person:
                    summary: Operation over persons
                    put:
                      summary: Create new person
                      description: ''
                      operationId: create-person
                      responses:
                        default:
                          description: Default error sample response
                """;
        var result = new OpenAPIParser().readContents(specification, null, null);
        assertThat(result.getMessages()).isEmpty();
        var openAPI = result.getOpenAPI();
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getPaths().get("/person")).isNotNull();
    }
}
