package collection;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CollectionFactoryMethods {
    @Test
    public void list() {
        List<Integer> list = List.of(1, 2, 3);
        assertThat(list, contains(1, 2, 3));
    }

    @Test
    public void set() {
        Set<Integer> set = Set.of(1, 2, 3);
        assertThat(set, containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void map() {
        Map<String, Integer> map = Map.of("age", 30, "number", 1);
        assertThat(map, hasEntry("age", 30));
        assertThat(map, hasEntry("number", 1));
    }
}
