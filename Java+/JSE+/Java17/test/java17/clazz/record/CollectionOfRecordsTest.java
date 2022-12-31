package java17.clazz.record;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionOfRecordsTest {

    @Test
    void sortList() {
        var john = new Person1(1L, "John");
        var mary = new Person1(2L, "Mary");
        var jack = new Person1(3L, "Jack");
        var list = new ArrayList<>(List.of(john, mary, jack));
        assertThat(list).containsExactly(john, mary, jack);
        list.sort(Comparator.comparing(o -> o.name));
        assertThat(list).containsExactly(jack, john, mary);
    }

    @Test
    void sortSetComparable() {
        var john = new Person2(1L, "John");
        var mary = new Person2(2L, "Mary");
        var jack = new Person2(3L, "Jack");
        var set = new TreeSet<>(Set.of(john, mary, jack));
        assertThat(set).containsExactly(jack, john, mary);
    }

    @Test
    void sortSetComparator() {
        var john = new Person1(1L, "John");
        var mary = new Person1(2L, "Mary");
        var jack = new Person1(3L, "Jack");
        var set = new TreeSet<>(Comparator.comparing(Person1::name));
        set.addAll(Set.of(john, mary, jack));
        assertThat(set).containsExactly(jack, john, mary);
    }

    private record Person1(Long id, String name) {
    }

    private record Person2(Long id, String name) implements Comparable<Person2> {
        @Override
        public int compareTo(Person2 other) {
            return name.compareTo(other.name);
        }
    }


}
