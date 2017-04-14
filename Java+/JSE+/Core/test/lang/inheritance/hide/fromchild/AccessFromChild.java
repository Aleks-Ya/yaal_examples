package lang.inheritance.hide.fromchild;
import org.junit.Test;

import static java.lang.System.out;

/**
 * Сокрытие поля суперкласса и доступ к нему из дочернего класса.
 */
public class AccessFromChild {
    @Test
    public void main() {
        new Child().printNames();
    }
}

class Parent {
    String name = "Parent";
}

class Child extends Parent {
    String name = "Child";
    
    void printNames() {
        out.printf("super.name = %s%n", super.name);
        out.printf("this.name  = %s%n", this.name);
        out.printf("name       = %s%n", name);
    }
}