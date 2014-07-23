import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
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