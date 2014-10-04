import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Добавление в коллекцию List объектов разных типов.
 */
public class AddDiffTypes {

    @Test
    public void main() {
        List<Object> list = new ArrayList<>();
        list.add(new String("1234"));
        list.add(new Person());
        list.add(new Emp());
        list.add(new String[]{"abcd", "xyz"});
        System.out.println(list);
    }
}

class Person {
}

class Emp extends Person {
}