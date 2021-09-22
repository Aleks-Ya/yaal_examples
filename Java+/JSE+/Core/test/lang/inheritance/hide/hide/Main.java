package lang.inheritance.hide.hide;

import org.junit.jupiter.api.Test;

/**
 * Сокрытие полей и методов в дочернем классе.
 */
class Main {

    @Test
    void main() {
        Parent p = new Parent();
        p.field++;
        p.method();

        Child c = new Child();
        // Поле и метод недоступны:
        //c.field++;
        //c.method();
    }
}

class Parent {
    public int field;

    public void method() {
    }
}

class Child extends Parent {
    private int field;

    public void method() {
    }
}