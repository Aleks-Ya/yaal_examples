package util.collection.map;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class CreateMapTest {
    @Test
    void ofEntries() {
        var map = Map.ofEntries(entry("a", 1), entry("b", 2));
        assertThat(map).containsEntry("a", 1).containsEntry("b", 2);
    }
}
