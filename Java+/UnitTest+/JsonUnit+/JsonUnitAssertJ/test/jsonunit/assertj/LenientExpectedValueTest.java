package jsonunit.assertj;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LenientExpectedValueTest {
    @Test
    void test() {
        assertThatJson("""
                {
                    "f1": "abc",
                    "f2": 2,
                    "f3": "John Smith",
                    "f4": {
                        "f5": "xyz",
                        "f6": 7
                    }
                }""")
                .isEqualTo("{f2:2, f1:abc, f3:'John Smith', f4: {f5:xyz, f6:7}}");
    }
}
