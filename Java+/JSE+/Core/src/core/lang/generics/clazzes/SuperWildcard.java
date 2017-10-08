package core.lang.generics.clazzes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Пример использования нижнего ограничения группового символа (? super MyClass).
 */
public class SuperWildcard {

    @Test
    public void test() {
        Storage<List<? extends Collection>> s = new Storage<>();
        List<Set> sets = new ArrayList<>();
        s.put(sets);

        Collection<List<? extends Collection>> queues = new ArrayList<>();
        s.addMultiple(queues, 10);
        Assert.assertThat(queues, Matchers.hasSize(10));
    }

    @Test
    public void get() {
        Storage<List<? extends Collection>> s = new Storage<>();
        List<Set> sets = new ArrayList<>();
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
