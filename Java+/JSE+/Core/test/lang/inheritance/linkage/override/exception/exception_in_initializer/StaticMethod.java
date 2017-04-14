package lang.inheritance.linkage.override.exception.exception_in_initializer;

public class StaticMethod {
    static int i = getInt();

    static int getInt() {
        throw new RuntimeException();
    }
}