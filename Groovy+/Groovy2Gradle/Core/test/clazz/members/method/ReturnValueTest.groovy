package clazz.members.method

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class MyClass {
    static List<String> getStringList() {
        return ["a", "b"]
    }

    static void returnVoid() {
    }
}

class ReturnValueTest {
    @Test
    void getStringList() {
        assertEquals(["a", "b"], MyClass.getStringList())
    }

    @Test
    void getVoid() {
        MyClass.returnVoid()
    }
}
