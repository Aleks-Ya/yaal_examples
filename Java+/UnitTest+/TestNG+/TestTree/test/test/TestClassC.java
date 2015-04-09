package test;

import base.BaseTestClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(dependsOnGroups = "B")
public class TestClassC extends BaseTestClass {

    @Test(groups = "C")
    public void testMain() {
        assertEquals("Right state after B", title);
        title = "Right state after C";
    }

    @Test(dependsOnMethods = "testMain")
    public void otherTest() {
        assertEquals("Right state after B", title);
    }
}
