package ru.yaal.examples.gradle.pitest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MyClassTest {
    @Test
    public void test() {
        MyClass my = new MyClass();
        assertEquals(my.math(1,3), 4);
        assertEquals(my.math(-2,3), -6);
    }
}
