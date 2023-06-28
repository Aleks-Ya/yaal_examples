package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonNotEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

class OrderTest {
    @Test
    void ignoreTokenOrder() {
        var exp = """
                {"name":"John", "age": 30}""";
        var act = """
                {"age": 30, "name":"John"}""";
        assertJsonEquals(exp, act);
    }

    @Test
    void ignoreArrayOrder() {
        var exp = "[1,2,3]";
        var act = "[3,2,1]";
        assertJsonNotEquals(exp, act);
        assertJsonEquals(exp, act, when(IGNORING_ARRAY_ORDER));
    }
}
