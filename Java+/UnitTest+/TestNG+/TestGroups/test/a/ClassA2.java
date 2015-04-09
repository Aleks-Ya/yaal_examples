package a;

import org.testng.annotations.Test;

public class ClassA2 {
    @Test(groups = "a_not_run")
    public void testName() {
        System.out.println("ClassA2");
    }
}
