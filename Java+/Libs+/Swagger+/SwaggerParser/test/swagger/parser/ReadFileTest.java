package swagger.parser;

import io.swagger.parser.OpenAPIParser;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class ReadFileTest {
    @Test
    void read() {
        var file = ResourceUtil.resourceToPath(ReadFileTest.class, "ReadFileTest.yaml");
        var result = new OpenAPIParser().readLocation(file, null, null);
        assertThat(result.getMessages()).isEmpty();
        var openAPI = result.getOpenAPI();
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getPaths().get("/person")).isNotNull();
    }
}
