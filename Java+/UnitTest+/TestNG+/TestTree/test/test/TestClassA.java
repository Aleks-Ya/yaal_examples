package test;

import base.BaseTestClass;
import base.SaveRestoreHandler;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestClassA extends BaseTestClass {

    @Test(groups = "A")
    public void testMain() {
        assertEquals(title, "Initial state");
        title = "Right state after A";
    }

    @Test(dependsOnMethods = "testMain")
    public void otherTest() {
        assertEquals(title, "Initial state");
        title = "Wrong state after A";
    }
}
