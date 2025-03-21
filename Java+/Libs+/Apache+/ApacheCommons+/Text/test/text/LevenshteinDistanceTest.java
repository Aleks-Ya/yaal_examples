package text;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevenshteinDistanceTest {
    @Test
    void toCamelCase() {
        var levenshteinDistance = LevenshteinDistance.getDefaultInstance();
        int distance = levenshteinDistance.apply("string1", "string2");
        assertThat(distance).isEqualTo(1);
    }
}
