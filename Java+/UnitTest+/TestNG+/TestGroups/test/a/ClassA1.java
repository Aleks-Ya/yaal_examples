package a;

import org.testng.annotations.Test;

public class ClassA1 {
    @Test(groups = "a_to_run")
    public void test() {
        System.out.println("ClassA1");
    }
}
