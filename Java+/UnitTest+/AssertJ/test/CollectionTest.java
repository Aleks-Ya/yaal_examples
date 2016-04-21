import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionTest {

    @Test
    public void chain() {
        assertThat(asList("a", "b", "c")).hasSize(3).contains("b").doesNotContain("z");
    }

    @Test
    public void filteredOn() {
        assertThat(asList("a", "bb", "c"))
                .filteredOn(s -> s.length() == 1)
                .containsOnly("a", "c");
    }
}
