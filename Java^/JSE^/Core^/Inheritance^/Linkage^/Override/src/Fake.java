package fake;

/**
 * Модификатор доступа переопределяющего метода может быть менее строгим.
 */
public class Fake {
    public static void main(String[] args) {
        //System.out.println(new Child().makeData());
    }
}

class Parent {
    String makeData() {
        return "Parent";
    }
}

class Child extends Parent {
    //Ошибка компиляции: return type int is not compatible with String
    //int makeData() {
    //    return 1;
    //}
}