package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование конструкции forEach().
 */
class ForEach {

    @Test
    void methodReference() {
        var source = List.of(-1, 0, 1);
        source.forEach(System.out::println);
    }

    @Test
    void var() {
        var source = List.of(Storage.MINUS_ONE, Storage.ZERO, Storage.ONE);
        source.forEach(n -> n.num++);
        assertThat(source).contains(Storage.ZERO, Storage.ONE, Storage.TWO);
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
