package test;

import base.BaseTestClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@Test(dependsOnGroups = "A")
public class TestClassB extends BaseTestClass {

    @Test(groups = "B")
    public void testMain() {
        assertEquals("Right state after A", title);
        title = "Right state after B";
    }

    @Test(dependsOnMethods = "testMain")
    public void otherTest() {
        assertEquals("Right state after A", title);
        title = "Wrong state after B";
    }
}
