package util.collection.array_list.add;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Добавление в коллекцию List объектов разных типов.
 */
class AddTest {

    @Test
    void different() {
        List<Object> list = new ArrayList<>();
        list.add(new String("1234"));
        list.add(new Person());
        list.add(new Emp());
        list.add(new String[]{"abcd", "xyz"});
        list.add(null);
        list.add(null);
        System.out.println(list);
    }

    /**
     * В ArrayList можно добавлять дублирующие элементы (в т.ч. несколько null).
     */
    @Test
    void duplicate() {
        Integer i = 100;
        List<Object> list = new ArrayList<>();
        list.add(i);
        list.add(i);
        list.add(null);
        list.add(null);
        assertThat(list).hasSize(4);
    }

    /**
     * В ArrayList можно добавлять null несколько раз.
     */
    @Test
    void addNull() {
        List<Object> list = new ArrayList<>();
        list.add(null);
        list.add(null);
    }


    class Person {
    }

    class Emp extends Person {
    }
}
