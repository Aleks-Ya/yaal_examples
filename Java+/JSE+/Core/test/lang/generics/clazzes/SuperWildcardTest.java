package lang.generics.clazzes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Пример использования нижнего ограничения группового символа (? super MyClass).
 */
class SuperWildcardTest {

    @Test
    void test() {
        Storage<List<? extends Number>> s = new Storage<>();
        List<Integer> sets = new ArrayList<>();
        s.put(sets);

        Collection<List<? extends Number>> queues = new ArrayList<>();
        s.addMultiple(queues, 10);
        assertThat(queues).hasSize(10);
    }

    @Test
    void get() {
        Storage<List<? extends Number>> s = new Storage<>();
        List<Integer> sets = new ArrayList<>();
        s.put(sets);
    }

    static class Storage<T> {
        T element;

        void put(T element) {
            this.element = element;
        }

        void addMultiple(Collection<? super T> collection, int times) {
            for (int i = 0; i < times; i++) {
                collection.add(element);
            }
        }

    }
}
