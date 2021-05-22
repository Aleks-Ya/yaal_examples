package myobject;

public class MyObject {
    public int number = 4;
    public String text = "abc";
    public transient boolean excluded;
    public Inner inner = new Inner();
}
