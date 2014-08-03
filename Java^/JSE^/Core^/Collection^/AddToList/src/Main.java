import java.util.List;
import java.util.ArrayList;

/**
 * Сортировка элементов карты с помощью интерфейса Comparable.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(new String("1234"));
        list.add(new Person());
        list.add(new Emp());
        list.add(new String[]{"abcd", "xyz"});
        System.out.println(list);
    }
}

class Person {}
class Emp extends Person {}