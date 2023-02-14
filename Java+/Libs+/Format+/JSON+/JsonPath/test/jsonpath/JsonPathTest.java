package jsonpath;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class JsonPathTest {

    @Test
    void field() {
        var json = """
                {"name": "John"}
                """;
        String name = JsonPath.read(json, "$.name");
        assertThat(name).isEqualTo("John");
    }

    @Test
    void array() {
        var json = """
                [{"name": "John"}, {"name": "Mary"}]
                """;
        String name = JsonPath.read(json, "$.[0].name");
        assertThat(name).isEqualTo("John");
    }
}
