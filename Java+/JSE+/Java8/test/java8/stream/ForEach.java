package java8.stream;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

/**
 * Использование конструкции forEach().
 */
public class ForEach {

    @Test
    public void methodReference() {
        var source = Arrays.asList(-1, 0, 1);
        source.forEach(System.out::println);
    }

    @Test
    public void var() {
        var source = Arrays.asList(Storage.MINUS_ONE, Storage.ZERO, Storage.ONE);
        source.forEach(n -> n.num++);
        assertThat(source, hasItems(Storage.ZERO, Storage.ONE, Storage.TWO));
    }

    static class Storage {
        static final Storage MINUS_ONE = new Storage(-1);
        static final Storage ZERO = new Storage(0);
        static final Storage ONE = new Storage(0);
        static final Storage TWO = new Storage(0);
        int num;

        Storage(int num) {
            this.num = num;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            var storage = (Storage) o;

            return num == storage.num;

        }

        @Override
        public int hashCode() {
            return num;
        }
    }
}
