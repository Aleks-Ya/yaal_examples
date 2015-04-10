package dependecies;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MyRunner.class)
@DependsOn(DependenciesTestA.class)
public class DependenciesTestB {
    @Test
    public void testInTestB() {
        System.out.println("My test method");
    }
}
