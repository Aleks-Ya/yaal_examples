/**
 * Попытка переопределить метод, используя аргумент дочернего типа: нельзя.
 */
public class DerivedParameter {

    private static class Parent {
        void method(Exception e) {
        }
    }

    private static class Child extends Parent {
        //@Override //Compile error
        void method(RuntimeException e) {
        }
    }
}