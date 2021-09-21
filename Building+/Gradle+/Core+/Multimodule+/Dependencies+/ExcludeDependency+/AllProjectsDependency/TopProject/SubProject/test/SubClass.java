import org.junit.Test;

public class SubClass {
    @Test(expected = ClassNotFoundException.class)
    public void main() throws ClassNotFoundException {
        Class.forName("org.joda.time.DateTime");
    }
}