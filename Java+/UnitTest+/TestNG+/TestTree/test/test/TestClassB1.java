package test;

import base.BaseTestClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(dependsOnGroups = "A1")
public class TestClassB1 extends BaseTestClass {

    @Test(groups = "B1")
    public void testMain() {
        assertEquals("State after A1", title);
    }
}
