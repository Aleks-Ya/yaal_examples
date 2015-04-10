package dependecies;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MyRunner.class)
public class DependenciesTestA {
    @Test
    public void testInTestA() {
        System.out.println("My test method");
    }
}
