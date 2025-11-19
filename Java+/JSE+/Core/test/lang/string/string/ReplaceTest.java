package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplaceTest {

    @Test
    void replaceAllNoRegex() {
        assertThat("ababab".replace("a", "")).isEqualTo("bbb");
    }

    @Test
    void replaceQuotes() {
        var replacement = """
                ["red", "green"]""";
        var act = "colors={{colors}}".replace("{{colors}}", replacement);
        assertThat(act).isEqualTo("""
                colors=["red", "green"]""");
    }

    @Test
    void replaceQuotesEscaped() {
        var replacement = """
                ["red", "green"]""";
        var escapedReplacement = replacement.replace("\"", "\\\"");
        var act = "colors={{colors}}".replace("{{colors}}", escapedReplacement);
        assertThat(act).isEqualTo("""
                colors=[\\"red\\", \\"green\\"]""");
    }

    @Test
    void replaceAllQuotesEscaped() {
        var replacement = """
                ["red", "green"]""";
        var escapedReplacement = replacement.replace("\"", "\\\\\"");
        var act = "colors={{colors}}".replaceAll("\\{\\{colors}}", escapedReplacement);
        assertThat(act).isEqualTo("""
                colors=[\\"red\\", \\"green\\"]""");
    }

}
