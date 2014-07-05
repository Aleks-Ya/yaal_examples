import org.junit.Test;

public class ExceptionIninitializerErrorTest {

    @Test(expected = ExceptionInInitializerError.class)
    public void staticField() {
        new StaticField();
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void staticInitializer() {
        new StaticInitializer();
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void staticMethod() {
        new StaticMethod();
    }
}