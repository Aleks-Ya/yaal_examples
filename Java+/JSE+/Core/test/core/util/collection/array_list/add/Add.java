package core.util.collection.array_list.add;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Добавление в коллекцию List объектов разных типов.
 */
public class Add {

    @Test
    public void different() {
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
    public void duplicate() {
        Integer i = 100;
        List<Object> list = new ArrayList<>();
        list.add(i);
        list.add(i);
        list.add(null);
        list.add(null);
        assertEquals(4, list.size());
    }

    /**
     * В ArrayList можно добавлять null несколько раз.
     */
    @Test
    public void addNull() {
        List<Object> list = new ArrayList<>();
        list.add(null);
        list.add(null);
    }


    class Person {
    }

    class Emp extends Person {
    }
}
