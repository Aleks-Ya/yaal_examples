package ru.yaal.examples.testng.testngxml.pack1;

import org.testng.annotations.Test;
import ru.yaal.examples.testng.testngxml.ClassOne;

import static org.testng.Assert.assertEquals;

@Test(groups = {"checkin-test"})
public class ClassOneThirdTest {
    @Test(groups = {"third_group"})
    public void third() throws Exception {
        ClassOne one = new ClassOne();
        assertEquals(one.getStr(), "val");
    }
}
