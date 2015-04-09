package test;

import base.BaseTestClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestClassA1 extends BaseTestClass {

    @Test(groups = "A1")
    public void testMain() {
        assertEquals("Initial state", title);
        title = "State after A1";
    }
}
