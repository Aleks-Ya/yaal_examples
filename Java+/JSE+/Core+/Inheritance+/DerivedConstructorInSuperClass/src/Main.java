import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Child ch = new Child();
        Parent p = new Parent(ch);
        out.println(ch);
        out.println(p);
    }
  }

class Parent {
    Parent(){}
    Parent(Child ch) {}
}

class Child extends Parent {}