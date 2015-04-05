import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        
        //compile error
        //out.println(p.name);
    }
}

class Parent {}

class Child extends Parent {
    String name = "Child";
}