package util.collection.array_list.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SortListTest {
    private final List<String> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list.add("c");
        list.add("b");
        list.add("a");
    }

    @Test
    void listSort() {
        assertThat(list).containsExactly("c", "b", "a");
        list.sort(String::compareTo);
        assertThat(list).containsExactly("a", "b", "c");
    }

    @Test
    void collectionsSort() {
        assertThat(list).containsExactly("c", "b", "a");
        Collections.sort(list);
        assertThat(list).containsExactly("a", "b", "c");
    }

}
