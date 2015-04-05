public class StaticMethod {
    static int i = getInt();

    static int getInt() {
        throw new RuntimeException();
    }
}