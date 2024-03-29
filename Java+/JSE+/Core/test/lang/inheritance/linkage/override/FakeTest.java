package lang.inheritance.linkage.override;

import org.junit.jupiter.api.Test;

/**
 * Модификатор доступа переопределяющего метода может быть менее строгим.
 */
class FakeTest {
    @Test
    void main() {
        //System.out.println(new Child().makeData());
    }


    private static class Parent {
        String makeData() {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        //Ошибка компиляции: return type int is not compatible with String
        //int makeData() {
        //    return 1;
        //}
    }
}