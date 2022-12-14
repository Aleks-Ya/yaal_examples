package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpacesTest {
    private static String removeDuplicateSpaces(String origin) {
        if (origin == null) {
            return null;
        }
        return origin.replaceAll("\\s{2,}", " ").trim();
    }

    @Test
    void replaceManySpacesWithOne() {
        assertThat(removeDuplicateSpaces("   ab  c e     f   ")).isEqualTo("ab c e f");
        assertThat(removeDuplicateSpaces(null)).isNull();
    }
}
