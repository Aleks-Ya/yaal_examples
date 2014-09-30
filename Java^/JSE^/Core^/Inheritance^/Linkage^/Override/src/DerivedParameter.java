/**
 * Попытка переопределить метод, используя аргумент дочернего типа: нельзя.
 */
public class DerivedParameter {
}

class Parent {
    void method(Exception e) {
    }
}

class Child extends Parent {
    //@Override //Compile error
    void method(RuntimeException e) {
    }
}

