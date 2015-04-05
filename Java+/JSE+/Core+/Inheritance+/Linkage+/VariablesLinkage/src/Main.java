import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Parent child = new Child();
        out.println(parent.name);
        out.println(child.name);
        out.println(parent.getName());
        out.println(child.getName());
    }
}

class Parent {
    String name = "Parent";
    String getName() {
        return name;
    }
}

class Child extends Parent {
    String name = "Child";
    
    String getName() {
        return name;
    }
}