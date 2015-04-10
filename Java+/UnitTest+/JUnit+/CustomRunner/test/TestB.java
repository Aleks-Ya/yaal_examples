import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MyRunner.class)
@DependsOn(TestA.class)
public class TestB {
    @Test
    public void testName() {
        System.out.println("My test method");
    }
}
