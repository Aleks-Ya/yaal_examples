package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

class TokenOrderTest {
    @Test
    void ignoreOrder() {
        var exp = """
                {"name":"John", "age": 30}""";
        var act = """
                {"age": 30, "name":"John"}""";
        assertJsonEquals(exp, act);
    }
}
